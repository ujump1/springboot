package com.yj.springboot.service.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.yj.springboot.entity.AccountVoucherPDFHead;
import com.yj.springboot.entity.AccountVoucherPDFItem;
import com.yj.springboot.service.config.AsianFontProvider;
import com.yj.springboot.service.responseModel.ResultData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.util.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/1/7 14:15
 */
@Component
public class PDFUtil {

	@Autowired
	FreeMarkerConfigurer configurer;
	@Autowired
	private MyBarcode4jUtil myBarcode4jUtil;
	@Autowired
	private ZxingUtil zxingUtil;

	private static final String FONT = "fonts/simsun.ttc";


	/******************ITextFontResolver*****************/

	/**
	 * 生成pdf
	 * 返回byte数组
	 */
	public byte[]  generatePDF(String htmlName,Map<String,Object> dataMap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ITextRenderer renderer = new ITextRenderer();
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(htmlName, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
			htmlData = htmlData.replace("&", "&amp;"); // 替换一下特殊符号
			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文乱码问题，fontPath为中文字体地址
			fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlData);
			renderer.layout();
			renderer.createPDF(byteArrayOutputStream);
		} catch (DocumentException | IOException | TemplateException e) {
			LogUtil.error("生成pdf失败:"+htmlName,e);
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(byteArrayOutputStream);
		}
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * 生成pdf文件
	 */
	public void  generatePDF(String htmlName,String fileUrl,Map<String,Object> dataMap) {
		 // 添加条行码（放外面也行)
		dataMap.put("barcode","\""+base64()+"\"");
		File file = new File(fileUrl);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ITextRenderer renderer = new ITextRenderer();
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(htmlName, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
			htmlData = htmlData.replace("&", "&amp;"); // 替换一下特殊符号

			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文乱码问题，fontPath为中文字体地址
			fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlData);

			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (DocumentException | IOException | TemplateException e) {
			LogUtil.error("生成pdf失败:"+htmlName,e);
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * 生成barcode并生成文件
	 */
	public void  generateBarcodePDF(String fileUrl) throws DocumentException {
		File file = new File(fileUrl);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Document doc = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, outputStream);
		doc.open();
		PdfContentByte pc = pdfWriter.getDirectContent();
		doc.add(new Paragraph("barcode"));
		BarcodeCodabar barcodeCodabar = new BarcodeCodabar();
		barcodeCodabar.setCode("A1234B");
		doc.add(barcodeCodabar.createImageWithBarcode(pc,null,null));
		doc.close();
	}

	/**
	 * 生成barcodeBase64
	 */
	public String base64() {
		String base64 = "";
		//byte[] bytes = myBarcode4jUtil.generate("A123BCD");
		byte[] bytes = zxingUtil.generateBarcode("李萍萍");
		try {
			base64 = "data:image/png;base64,"+new String(Base64.getEncoder().encode(bytes), "UTF-8");
		}catch (Exception e){
			LogUtil.error("异常"+e);
		}
		return base64;
	}



	/******************XMLWorkerHelper*****************/


	/**
	 * 生成pdf
	 * 返回byte数组
	 */
	public byte[]  generatePDFXMLWorkerHelper(String htmlName,Map<String,Object> dataMap) {
		byte[] bytes;
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(htmlName, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
			htmlData = htmlData.replace("&", "&amp;"); // 替换一下特殊符号
			bytes = buildPdf(htmlData,"A1234");
		} catch (IOException | TemplateException e) {
			LogUtil.error("生成pdf失败:" + htmlName, e);
			bytes = null;
		}
		return bytes;
	}


	/**
	 * 生成pdf文件
	 */
	public void  generatePDFXMLWorkerHelper(String htmlName,String fileUrl,Map<String,Object> dataMap) {
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(htmlName, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
			htmlData = htmlData.replace("&", "&amp;"); // 替换一下特殊符号
			 buildPdf(htmlData,"A1234",fileUrl);
		} catch (IOException | TemplateException e) {
			LogUtil.error("生成pdf失败:" + htmlName, e);
		}
	}

	/**
	 * XMLWorkerHelper根据htmldata生成pdf文件(可加条行码)
	 *
	 * @param content html内容
	 * @param barcode 条形码编号
	 * @return 报文
	 */
	private byte[] buildPdf(String content, String barcode) {
		ByteArrayOutputStream baos = null;
		byte[] array;
		try {
			//构建字节输出流
			baos = new ByteArrayOutputStream();
			//构建文档
			Document document = new Document();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
			document.open();
			//生成条形码 todo 暂时有点bug
			buildBarcode(document, pdfWriter, barcode);
			//通过html生成pdf
			XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,
					new ByteArrayInputStream(content.getBytes("UTF-8")), null, Charset.forName("UTF-8"), new AsianFontProvider());
			document.close();
			//转换成byte字符数组
			array = baos.toByteArray();
		} catch (Exception e) {
			LogUtil.error("生成文件异常！", e);
			return null;
		}finally {
			IOUtils.closeQuietly(baos);
		}
		return array;
	}

	/**
	 * XMLWorkerHelper根据htmldata生成pdf文件(可加条行码)
	 *
	 * @param content html内容
	 * @param barcode 条形码编号
	 * @return 报文
	 */
	private void buildPdf(String content, String barcode,String fileUrl) {
		File file = new File(fileUrl);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			//构建文档
			Document document = new Document();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
			document.open();
			//生成条形码
			buildBarcode(document, pdfWriter, barcode);
			//通过html生成pdf
			XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,
					new ByteArrayInputStream(content.getBytes("UTF-8")), null, Charset.forName("UTF-8"), new AsianFontProvider());
			document.close();
		} catch (Exception e) {
			LogUtil.error("生成文件异常！", e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * 生成条形码
	 *
	 * @param document
	 * @param pdfWriter
	 * @param barcode   条形码编号
	 * @return 报文
	 */
	private void buildBarcode(Document document, PdfWriter pdfWriter, String barcode) {
		try {
			PdfContentByte contentByte = new PdfContentByte(pdfWriter);
			Barcode128 code128 = new Barcode128();
			code128.setCode(barcode.trim());
			code128.setCodeType(Barcode128.CODE128);
			Image code128Image = code128.createImageWithBarcode(contentByte, null, null);
			//设置放在右上角
			code128Image.setAbsolutePosition(452, 800);
			//设置缩放比
			code128Image.scalePercent(100);
			document.add(code128Image);
		} catch (Exception e) {
			LogUtil.error("生成条形码异常！", e);
		}
	}


	public static void main(String[] args) throws Exception {
		PDFUtil pdfUtil = new PDFUtil();
		pdfUtil.generateBarcodePDF("C:\\Users\\YJ\\Desktop\\简历8.pdf");
		System.out.println(pdfUtil.base64());
	}




}
