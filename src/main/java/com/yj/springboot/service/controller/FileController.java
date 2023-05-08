package com.yj.springboot.service.controller;

import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.LogUtil;
import com.yj.springboot.service.utils.ZipUtils;
import com.yj.springboot.service.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.zip.ZipOutputStream;

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
		String filePath = "C:\\Users\\YJ\\Desktop\\QQ图片20210629133124.jpg";
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
		String fullPath = "C:\\Users\\YJ\\Desktop\\QQ图片20210629133124.jpg";
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

	/**
	 * 下载
	 * @param docId 文档id
	 * 这里ByteArrayResource为例,使用base64字符串，也可以用其他的哈，直接使用文件也行
	 */
	@GetMapping("downloadZip")
	@ApiOperation(value = "下载", notes = "下载")
	ResponseEntity<ByteArrayResource> downloadZip(@RequestParam("docId") @NotBlank String docId) throws IOException {
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
		String fileName1 = "QQ图片20210629133124.jpg";
		String fileName2 = "QQ图片20210629133123.jpg";
		// 获取文件字节数组(这里其实可以直接用file得到,多了一步加密解密，是为了演示只能拿到base64字符串的情况)
		byte[] bytes = Base64Utils.decodeFromString(base64Source);
		// 输出
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 如果是文件的话输出流直接用flieOutputStream ByteArrayOutputStream可以理解为暂存在内存中
		ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream);
		// 这里不用添加两次模拟for循环多个文件
		try {
			ZipUtils.zipFile(fileName1, new ByteArrayInputStream(bytes), zos);
			ZipUtils.zipFile(fileName2, new ByteArrayInputStream(bytes), zos);
			zos.flush();
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		try {
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",new String( "测试ZIP.zip".getBytes("UTF-8"), "ISO8859-1" )));
		} catch (UnsupportedEncodingException e) {
			// 文件名转换失败
			LogUtil.error("发票下载失败:文件名转换失败"+"测试ZIP.zip");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.parseMediaType("application/octet-stream;charset=UTF-8"))
				.body(new ByteArrayResource(byteArrayOutputStream.toByteArray()));
	}



	// 下面是使用responseEntity和httpResponse的区别

	/**
	 * ResponseEntity图片预览
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/responseEntityView")
	public ResponseEntity<byte[]> responseEntityView() throws Exception {
		File file = new File("E:\\图片\\bpic4828.jpg");
		InputStream inputStream = new FileInputStream(file);
		byte[] bytes = null;
		bytes = readInputStream(inputStream);

		HttpHeaders httpHeaders = new HttpHeaders();
		// 不是用缓存
		httpHeaders.setCacheControl(CacheControl.noCache());
		httpHeaders.setPragma("no-cache");
		httpHeaders.setExpires(0L);
		httpHeaders.setContentType(MediaType.IMAGE_JPEG);
		ResponseEntity responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * ResponseEntity文件下载Post请求
	 * （如果不会参数不多就最好使用get,post请求不会解码文件名，如果不编码中文就会成一个_，没有中文用那个都可以）
	 *
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/responseEntityDownloadPost")
	public ResponseEntity<byte[]> responseEntityDownloadPost() throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tt\\小儿喜.zip");
		InputStream inputStream = new FileInputStream(file);
		byte[] bytes = null;
		bytes = readInputStream(inputStream);
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", fileName);
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ResponseEntity responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * ResponseEntity文件下载Get请求
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/responseEntityDownloadGet")
	public ResponseEntity<byte[]> responseEntityDownloadGet() throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tt\\小儿喜.zip");
		InputStream inputStream = new FileInputStream(file);
		byte[] bytes = null;
		bytes = readInputStream(inputStream);
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", fileName);
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ResponseEntity responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * ResponseEntity文件下载Get请求PostPost请求不会自动解码，所以使用get请求转一下就没有问题了
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/responseEntityDownloadGetByPost")
	public ResponseEntity<byte[]> responseEntityDownloadGetByPost() throws Exception {
		ResponseEntity<byte[]> responseEntity = this.responseEntityDownloadPost();
		return responseEntity;
	}

	/**
	 * HttpServletResponse图片预览
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/httpServletResponseView")
	public String httpServletResponseView(HttpServletResponse response) throws Exception { // 返回void也行
		File file = new File("E:\\图片\\bpic4828.jpg");
		InputStream inputStream = new FileInputStream(file);
		byte[] bytes = null;
		OutputStream outputStream = response.getOutputStream();
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// 不使用缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		response.setHeader("Content-Disposition", "inline");
		readInputStreamToOutStream(inputStream, outputStream);
		return null;
	}


	/**
	 * HttpServletResponse文件下载Post请求
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/httpServletResponseDownloadPost")
	public String httpServletResponseDownloadPost(HttpServletResponse response) throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tt\\小儿喜.zip");
		InputStream inputStream = new FileInputStream(file);
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setCharacterEncoding("UTF-8");
		OutputStream outputStream = response.getOutputStream();
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		readInputStreamToOutStream(inputStream, outputStream);
		return null;
	}

	/**
	 * HttpServletResponse文件下载Get请求
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/httpServletResponseDownloadGet")
	public String httpServletResponseDownloadGet(HttpServletResponse response) throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tt\\小儿喜.zip");
		InputStream inputStream = new FileInputStream(file);
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setCharacterEncoding("UTF-8");
		OutputStream outputStream = response.getOutputStream();
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		readInputStreamToOutStream(inputStream, outputStream);
		return null;
	}

	/**
	 * HttpServletResponse文件下载Get请求通过Post生成数据
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/httpServletResponseDownloadGetByPost")
	public String httpServletResponseDownloadGetByPost(HttpServletResponse response) throws Exception {
		this.httpServletResponseDownloadPost(response);
		return null;
	}

	/**
	 * ResponseEntity和 HttpServletResponse文件下载Get
	 * 涉及到HttpMessageConverter 里面的 HandlerMethodReturnValueHandler
	 * 总之就是HttpMessageConverter会把ResponseEntity的信息放到response中
	 * 个人猜测：如果HttpServletResponse的body（outStream）已经有值的话，ResponseEntity的东西就不能覆盖HttpServletResponse中名字一样的值
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/dwonlaodUnion")
	public  ResponseEntity<byte[]> dwonlaodUnion(HttpServletResponse response) throws Exception {



		//操作response
		File file = new File("C:\\Users\\YJ\\Desktop\\新建 文本文档.txt");
		InputStream inputStream = new FileInputStream(file);
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setCharacterEncoding("UTF-8");
		OutputStream outputStream = response.getOutputStream();
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		readInputStreamToOutStream(inputStream, outputStream);

		// 操作ResponseEntity
		File file1 = new File("C:\\Users\\YJ\\Desktop\\发布.txt");
		byte[] bytes = null;
		InputStream inputStream1 = new FileInputStream(file1);
		bytes = readInputStream(inputStream1);
		String fileName1 = URLEncoder.encode(file1.getName(), "UTF-8");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=" + fileName1);
		httpHeaders.setContentType(MediaType.APPLICATION_PDF);
		ResponseEntity responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);

		return responseEntity;
	}


	/**
	 * 输入流装成字节数组
	 *
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 输入流写进输出流
	 *
	 * @param inStream
	 * @param outStream
	 * @throws Exception
	 */
	private void readInputStreamToOutStream(InputStream inStream, OutputStream outStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		outStream.close();
	}

}
