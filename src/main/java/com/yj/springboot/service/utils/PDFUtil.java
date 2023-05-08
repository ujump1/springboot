package com.yj.springboot.service.utils;

import com.google.common.primitives.Bytes;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/1/7 14:15
 */
@Component
public class PDFUtil {

	@Autowired
	FreeMarkerConfigurer configurer;

	private static final String FONT = "fonts/simsun.ttc";

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
	 * 生成pdf文件
	 */
	public void  generatePDF(String htmlData,String fileUrl) {
		File file = new File(fileUrl);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ITextRenderer renderer = new ITextRenderer();
		try {
			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文乱码问题，fontPath为中文字体地址
			fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlData);
			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (DocumentException | IOException e) {
			LogUtil.error("生成pdf失败:",e);
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(outputStream);
		}
	}
}
