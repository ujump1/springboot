package com.yj.springboot.service.controller;

import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.*;
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
		try {
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",new String( fileName.getBytes("UTF-8"), "ISO8859-1" )));
		} catch (UnsupportedEncodingException e) {
			// 文件名转换失败
			LogUtil.error("发票下载失败:文件名转换失败"+fileName);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.parseMediaType("application/octet-stream;charset=UTF-8"))
				.body(new ByteArrayResource(bytes));
	}


	/**
	 * 下载
	 * @param docId 文档id
	 * 这里FileSystemResource为例
	 */
	@GetMapping("download1")
	@ApiOperation(value = "下载", notes = "下载")
	ResponseEntity<FileSystemResource> download1(@RequestParam("docId") @NotBlank String docId) throws IOException {
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
				.body(file);

	}

	@RequestMapping(value="/download2", method=RequestMethod.GET)
	public void download2(Long id, HttpServletRequest request, HttpServletResponse response) {

		// Get your file stream from wherever.
		String fullPath = "E:/" + id +".rmvb";
		File downloadFile = new File(fullPath);

		ServletContext context = request.getServletContext();

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
			System.out.println("context getMimeType is null");
		}
		System.out.println("MIME type: " + mimeType);

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// Copy the stream to the response's output stream.
		try {
			InputStream myStream = new FileInputStream(fullPath);
			IOUtils.copy(myStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
