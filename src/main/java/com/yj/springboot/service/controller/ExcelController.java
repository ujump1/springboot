package com.yj.springboot.service.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yj.springboot.entity.easypoi.ExperienceInputEntity;
import com.yj.springboot.entity.easypoi.TalentUserInputEntity;
import com.yj.springboot.entity.search.Search;
import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.DateUtil;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "excel")
@Api(value ="excel", tags = "excel操作")
public class ExcelController {

	/**
	 * 导入
	 * @param multipartFile
	 * @return
	 */
	@PostMapping("/import")
	@ApiOperation(value = "导入", notes = "导入excel")
	public ResultData importExcel(@RequestParam("file") MultipartFile multipartFile) {
		ImportParams params = new ImportParams();
		params.setTitleRows(1); // 标题行数  默认是0
		params.setHeadRows(2); // 表头的行数
		List<TalentUserInputEntity> result = null;
		try {
			result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),
					TalentUserInputEntity.class, params);
		} catch (Exception e) {
			LogUtil.error("导入异常:",e);
			return ResultData.fail("导入异常，请查看日志");
		}
		System.out.println(JsonUtils.toJson(result));
		return ResultData.success();
	}
	/**
	 * 导出
	 *
	 * @param search   查询参数
	 * @param response http响应
	 * @return 文件
	 */
	@PostMapping(path = "export")
	@ApiOperation(value = "导出", notes = "导出excel")
	public ResponseEntity<ByteArrayResource> export(@RequestBody @Valid Search search, HttpServletResponse response){
		// 组装数据
		// 第一条
		List<TalentUserInputEntity> talentUserInputEntities = new ArrayList<>();
		TalentUserInputEntity talentUserInputEntity = new TalentUserInputEntity();
		talentUserInputEntity.setName("余江");
		talentUserInputEntity.setGender(0);
		talentUserInputEntity.setBirth(DateUtil.parseDate("1996-11-05",DateUtil.DEFAULT_DATE_FORMAT));
		List<ExperienceInputEntity> experienceList = new ArrayList<>();
		ExperienceInputEntity experienceInputEntity = new ExperienceInputEntity();
		experienceInputEntity.setCompanyName("公司1");
		experienceInputEntity.setDepartment("部门1");
		experienceList.add(experienceInputEntity);
		ExperienceInputEntity experienceInputEntity2 = new ExperienceInputEntity();
		experienceInputEntity2.setCompanyName("公司2");
		experienceInputEntity2.setDepartment("部门2");
		experienceList.add(experienceInputEntity2);
		talentUserInputEntity.setExperienceList(experienceList);
		talentUserInputEntities.add(talentUserInputEntity);
		// 第二条
		TalentUserInputEntity talentUserInputEntity2 = new TalentUserInputEntity();
		talentUserInputEntity2.setName("余江2");
		talentUserInputEntity2.setGender(1);
		talentUserInputEntity2.setBirth(DateUtil.parseDate("1996-11-05",DateUtil.DEFAULT_DATE_FORMAT));
		talentUserInputEntities.add(talentUserInputEntity2);
		try {
			Workbook workbook;
			String fileName;
			fileName = String.format("简历_%s", DateUtil.formatDate(new Date(), DateUtil.DEFAULT_TIME_FORMAT));
			workbook = ExcelExportUtil.exportExcel(new ExportParams(fileName, fileName), // sheetName有中文就不行
					TalentUserInputEntity.class, talentUserInputEntities);
			//加载文件
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			workbook.write(stream);
			workbook.close();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\".xlsx",new String( fileName.getBytes("UTF-8"), "ISO8859-1" )))
					.header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
					.body(new ByteArrayResource(stream.toByteArray()));
		} catch (IOException e) {
			LogUtil.error("下载文件失败", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * 导出2
	 *
	 * @param search   查询参数
	 * @return 文件
	 */
	@PostMapping(path = "export2")
	@ApiOperation(value = "导出2", notes = "导出excel")
	public ResponseEntity<ByteArrayResource> export2(@RequestBody @Valid Search search){
		return null;
	}

	/**
	 * 导出1
	 *
	 * @param request   请求
	 * @param response http响应
	 * @return 文件
	 */
	@PostMapping(path = "export1")
	@ApiOperation(value = "导出1", notes = "导出excel")
	public ResponseEntity<ByteArrayResource> export1(HttpRequest request, HttpServletResponse response){
		return null;
	}


}
