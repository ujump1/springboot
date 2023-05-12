package com.yj.springboot.service.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YJ zxing生成条码
 * @version 1.0.1
 * @create 2023/5/12 12:40
 */
@Component
public class ZxingUtil {


	/**
	 * 生成二维码
	 * @param text
	 * @return
	 */
	public byte[] generateBarcode(String text) {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		String content = text;//这里content为二维码数据。为地址将直接访问，为text将直接解析
		int width = 200; // 图像宽度
		int height = 200; // 图像高度
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 4);//设置二维码边的空度，非负数，默认值为4。
		BitMatrix bitMatrix = null;// 生成矩阵
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		//MatrixToImageWriter提供多种方式输出二维码，并同时提供二维码其他信息配置的重载方法
		try {
			MatrixToImageWriter.writeToStream(bitMatrix, format, ous);// 输出图像
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(ous);
		}
		return ous.toByteArray() ;
	}
}
