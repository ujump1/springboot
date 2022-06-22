package com.yj.springboot;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.yj.springboot.entity.AccountVoucherPDFHead;
import com.yj.springboot.entity.AccountVoucherPDFItem;
import com.yj.springboot.entity.AddressVo;
import com.yj.springboot.entity.UserVo;
import com.yj.springboot.service.config.AsianFontProvider;
import com.yj.springboot.service.utils.NumberToCN;
import com.yj.springboot.service.utils.PDFUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.util.IOUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author YJ
 * @version 1.0.1
 * @create 2022/1/6 15:27
 */
public class mainTest extends BaseTest{

	@Autowired
	FreeMarkerConfigurer configurer;

	private static final String PDF_HTML = "2.html";

	private static final String PDF_HTML3 = "3.html";

	private static final String PDF_HTML4 = "4.html";

	private static final String FONT = "fonts/simsun.ttc";


	@Test
	public void test(){

	}
	@Test
	 public void generate() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("name", "余江");
		File file = new File("C:\\Users\\YJ\\Desktop\\简历4.pdf");
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ITextRenderer renderer = new ITextRenderer();
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(PDF_HTML4, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文乱码问题，fontPath为中文字体地址
			fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlData);

			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (DocumentException | IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(outputStream);
		}
	}

	@Test
	public void generate2() {
		List<UserVo> userVoList=new ArrayList<>();
		for (int i=0;i<5;i++){
			UserVo userVo=new UserVo();
			userVo.setName("张三"+i);
			if(i!=3){
				userVo.setAddressVoList(Arrays.asList(new AddressVo("上海"+i),new AddressVo("北京"+i)));
			}
			userVo.setAge(i*5);
			if(i!=2){
				userVo.setBirthday(DateTime.now().plusDays(i).toDate());
			}
			userVoList.add(userVo);
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("userList", userVoList);
		File file = new File("C:\\Users\\YJ\\Desktop\\简历3.pdf");
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ITextRenderer renderer = new ITextRenderer();
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(PDF_HTML3, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 解决中文乱码问题，fontPath为中文字体地址
			fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlData);

			renderer.layout();
			renderer.createPDF(outputStream);
		} catch (DocumentException | IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(outputStream);
		}
	}


	/// 调试有问题
	@Test
	public void generate3() {
		Document document = new Document(PageSize.A2);
		document.open();
		File file = new File("C:\\Users\\YJ\\Desktop\\简历5.pdf");
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ITextRenderer renderer = new ITextRenderer();
		try {
			Configuration cfg = configurer.getConfiguration();
			Template template = cfg.getTemplate(PDF_HTML4, "utf-8");
			String htmlData = FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());
			PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(htmlData.getBytes("UTF-8")),null, Charset.forName("UTF-8"),new AsianFontProvider());
			System.out.println("123");

		} catch (DocumentException | IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			renderer.finishPDF();
			IOUtils.closeQuietly(outputStream);
		}
	}

	@Autowired
	private PDFUtil pdfUtil;

	@Test
	public void generate4(){
		Map<String,Object> dataMap = new HashMap<>();
		AccountVoucherPDFHead accountVoucherHeadDto = new AccountVoucherPDFHead();
		accountVoucherHeadDto.setAccountDate("2021-12-30");
		accountVoucherHeadDto.setBusinessSourceOrderNo("R000001");
		accountVoucherHeadDto.setCorporationName("草根知本");
		accountVoucherHeadDto.setCurrencyName("美元");
		accountVoucherHeadDto.setErpAccountNo("0000000001");
		accountVoucherHeadDto.setTotalDebitAmount("1000.01");
		accountVoucherHeadDto.setTotalLendAmount("1000.01");
		accountVoucherHeadDto.setUpCaseAmount(NumberToCN.number2CNMontrayUnit(new BigDecimal("1000.01")));
		List<AccountVoucherPDFItem> accountVoucherItemDtoList = new ArrayList<>();
		dataMap.put("head",accountVoucherHeadDto);
		AccountVoucherPDFItem accountVoucherItemDto = new AccountVoucherPDFItem();
		accountVoucherItemDto.setNote("测试文本");
		accountVoucherItemDto.setCsName("测试客户");
		accountVoucherItemDto.setLedgerAccountName("测试科目");
		accountVoucherItemDto.setCostCenterName("测试成本中心");
		accountVoucherItemDto.setDebitAmount("1000.01");
		accountVoucherItemDtoList.add(accountVoucherItemDto);
		AccountVoucherPDFItem accountVoucherItemDto2 = new AccountVoucherPDFItem();
		accountVoucherItemDto2.setNote("测试文本");
		accountVoucherItemDto2.setCsName("测试客户");
		accountVoucherItemDto2.setLedgerAccountName("测试科目");
		accountVoucherItemDto2.setCostCenterName("测试成本中心");
		accountVoucherItemDto2.setLendAmount("1000.01");
		accountVoucherItemDtoList.add(accountVoucherItemDto2);
		dataMap.put("items",accountVoucherItemDtoList);
		pdfUtil.generatePDF("accountInfo.html","C:\\Users\\YJ\\Desktop\\简历6.pdf",dataMap);
	}


}
