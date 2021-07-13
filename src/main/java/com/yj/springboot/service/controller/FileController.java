package com.yj.springboot.service.controller;

import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping(value = "file")
@Api(value ="file", tags = "文件服务")
public class FileController {


	/**
	 * 上传
	 * @param file @文件
	 * @see com.yj.springboot.service.multipartFile.Base64ToMultipartFile;
	 * @param uploadUser 上传人
	 * @return 文档保存后id
	 */
	@PostMapping(value = "upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "上传", notes = "上传")
	ResultData<String> upload(@RequestPart("file") MultipartFile file,
							  @RequestParam(value = "uploadUser",required = false)  String uploadUser){

		// 上传操作

		return ResultData.success("id");
	}

	/**
	 * 下载
	 * @param docId 文档id
	 * 这里ByteArrayResource为例,使用base64字符串，也可以用其他的哈，直接使用文件也行
	 */
	@GetMapping("download")
	@ApiOperation(value = "下载", notes = "下载")
	ResponseEntity<ByteArrayResource> download(@RequestParam("docId") @NotBlank String docId) throws IOException {
		// 获取文件
		File file = new File("C:\\Users\\YJ\\Desktop\\QQ图片20210629133124.jpg");
		FileInputStream fin = null;
		fin = new FileInputStream(file);
		byte[] buff = new byte[fin.available()];
		fin.read(buff);
		// 获取base64编码的字符串（这里演示的是能拿到base64字符串，可以直接用file)
		String base64Source = Base64.getEncoder().encodeToString(buff);
		if(StringUtils.isEmpty(base64Source)){
			LogUtil.error("下载失败:"+docId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		// 获取文件名称
		String fileName = "QQ图片20210629133124.jpg";
		// 获取文件字节数组(这里其实可以直接用file得到,多了一步加密解密，是为了演示只能拿到base64字符串的情况)
		byte[] bytes = Base64Utils.decodeFromString(base64Source);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",fileName));
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.parseMediaType("application/octet-stream;charset=UTF-8"))
				.body(new ByteArrayResource(bytes));
	}

	/**
	 * 下载
	 * @param docId 文档id
	 * 这里InputStreamResource为例
	 */
	@GetMapping("download")
	@ApiOperation(value = "下载", notes = "下载")
	ResponseEntity<InputStreamResource> download1(@RequestParam("docId") @NotBlank String docId) throws IOException {
		// 获取文件
		String filePath = "E:/" + docId + ".rmvb";
		FileSystemResource file = new FileSystemResource(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));

	}
}
