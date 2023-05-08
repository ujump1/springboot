package com.yj.springboot.service.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yj.springboot.entity.easypoi.ExperienceInputEntity;
import com.yj.springboot.entity.easypoi.TalentUserInputEntity;
import com.yj.springboot.entity.search.PageInfo;
import com.yj.springboot.entity.search.Search;
import com.yj.springboot.service.config.MyExcelExportStylerImpl;
import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.DateUtil;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
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
import java.util.concurrent.atomic.AtomicInteger;

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
	 * 导出(隐藏行）
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
		talentUserInputEntity.setExperience1(experienceInputEntity2);
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
			// 设置格式
			ExportParams exportParams =new ExportParams(fileName, fileName, ExcelType.XSSF);
			exportParams.setStyle(MyExcelExportStylerImpl.class);
			workbook = ExcelExportUtil.exportExcel(exportParams, // sheetName有中文就不行
					TalentUserInputEntity.class, talentUserInputEntities);
			//加载文件
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			workbook.write(stream);
			workbook.close();
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\".xlsx",new String( fileName.getBytes("UTF-8"), "ISO8859-1" )))
//					.header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
//					.body(new ByteArrayResource(stream.toByteArray()));
			 	return ResponseEntity.ok()
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"")
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
		talentUserInputEntity.setExperience1(experienceInputEntity2);
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
			// 设置格式
			ExportParams exportParams =new ExportParams(fileName, fileName, ExcelType.XSSF);
			exportParams.setStyle(MyExcelExportStylerImpl.class);
			workbook = ExcelExportUtil.exportExcel(exportParams, // sheetName有中文就不行
					TalentUserInputEntity.class, talentUserInputEntities);
			// 添加一行隐藏行 给导入的时候前端用
			Sheet sheet = workbook.getSheetAt(0);
			// 重新设置冻结窗格
			sheet.createFreezePane(0,2,0,2);
			// 下移动一行
			sheet.shiftRows( 0, sheet.getLastRowNum(), 1,true,false);
			Row rowHidden = sheet.createRow(0); // 创建一行
			rowHidden.setZeroHeight(true); // 隐藏
			rowHidden.createCell(0).setCellValue("batchNumber");
			rowHidden.createCell(1).setCellValue("beforeMaterialCode");
			rowHidden.createCell(2).setCellValue("productName");
			rowHidden.createCell(3).setCellValue("productMergeName");
			rowHidden.createCell(4).setCellValue("rate");
			rowHidden.createCell(5).setCellValue("quantity");
			rowHidden.createCell(6).setCellValue("taxPrice");
			rowHidden.createCell(7).setCellValue("taxAmount");
			rowHidden.createCell(8).setCellValue("discountAmount");
			rowHidden.createCell(9).setCellValue("specification");
			rowHidden.createCell(10).setCellValue("projectUnit");
			rowHidden.createCell(11).setCellValue("remark");
			//加载文件
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			workbook.write(stream);
			workbook.close();
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\".xlsx",new String( fileName.getBytes("UTF-8"), "ISO8859-1" )))
//					.header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
//					.body(new ByteArrayResource(stream.toByteArray()));
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"")
					.header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
					.body(new ByteArrayResource(stream.toByteArray()));
		} catch (IOException e) {
			LogUtil.error("下载文件失败", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
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

	/**
	 * 大数据导入,使用的cn.afterturn.easypoi.excel为4.2.0
	 *
	 * @param search   查询条件
	 * @param response
	 * @return
	 */
	@PostMapping(path = "export3")
	@ApiOperation(value = "导出3", notes = "导出excel")
	public ResponseEntity<ByteArrayResource> export3(Search search, HttpServletResponse response) {
		//文件名称
		String fileName = "粘贴单签收记录_"
				+ DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xlsx";
		//excel导出的表头
		ExportParams params = new ExportParams("粘贴单签收记录", "粘贴单签收记录", ExcelType.XSSF);
		// 样式
		params.setStyle(MyExcelExportStylerImpl.class);

		// 获取导出数据
		int page = 1;
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page++);
		pageInfo.setRows(1500);
//		PageResult<DocumentSignRecord> pageResult = findByPage(search);
//		IWriter<Workbook> writer = ExcelExportUtil.exportBigExcel(params, DocumentSignRecord.class);
//		AtomicInteger no = new AtomicInteger(1);
//		while (CollectionUtils.isNotEmpty(pageResult.getRows())) {
//			List<DocumentSignRecordExportDto> documentSignRecordExportDtos = new ArrayList<>();
//			for(DocumentSignRecord documentSignRecord : pageResult.getRows()){
//				DocumentSignRecordExportDto item = dtoModelMapper.map(documentSignRecord, DocumentSignRecordExportDto.class);
//				// 设置序号
//				item.setNo(no.getAndIncrement());
//				// 枚举值赋值
//				item.setSignStatusRemark(EnumUtils.getEnumItemRemark(SignStatus.class, documentSignRecord.getSignStatus()));
//				documentSignRecordExportDtos.add(item);
//				//导入excel
//				writer.write(documentSignRecordExportDtos);
//				//获取下一页导出数据
//				search.getPageInfo().setPage(page++);
//				pageResult = findByPage(search);
//			}
//		}
//		Workbook workbook = writer.close();
		try {
			//加载文件
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//			workbook.write(stream);
//			workbook.close();
			String contentDisposition = ContentDisposition
					.builder("attachment")
					.filename(URLEncoder.encode(fileName, "UTF-8"))
					.build().toString();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new ByteArrayResource(stream.toByteArray()));
		} catch (IOException e) {
			LogUtil.error("导出数据出现异常！" + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


}
