package com.yj.springboot.service.utils;


import com.yj.springboot.service.responseModel.ResultData;
import org.apache.commons.lang3.StringUtils;


import java.security.MessageDigest;
import java.util.Calendar;

/**
 * 签名工具类
 * @author YJ
 * @version 1.0.1
 * @create 2022/7/12 10:02
 */
public class SignUtil {

	private String TG_SALT = "N364FB4OZ0BABM9J58BK9V7VPFYE49KP"; // 投管salt

	public static void main(String args[]){
		SignUtil signUtil = new SignUtil();
		String timeStamp = String.valueOf(Calendar.getInstance().getTime().getTime());
		String[] infos = {timeStamp, signUtil.TG_SALT}; // 测试系统salt
		StringBuilder strBuilder = new StringBuilder();
		for (Object info : infos) {
			strBuilder.append(info);
		}
		String sign = signUtil.sha1(strBuilder.toString());
		System.out.println("sign:"+sign);
		System.out.println("timeStamp:"+timeStamp);
	}


	/**
	 * 检查签名
	 * @param timeStamp 传入时间戳 1657593884959
	 * @param sign 传入签名
	 * @return
	 */
	public ResultData<?> checkSign(String timeStamp, String sign) {
		// 检查时间戳是否在前后五分钟
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 5);  // 加5分钟
		String timeStampAfter = String.valueOf(now.getTime().getTime());
		now.add(Calendar.MINUTE, -10);// 再减去10分钟
		String timeStampBefore = String.valueOf(now.getTime().getTime());
		if (StringUtils.isBlank(timeStamp)) {
			// C30001=时间戳不能为空!
			return ResultData.fail("C30001");
		}
		if (timeStamp.compareTo(timeStampAfter) > 0 || timeStamp.compareTo(timeStampBefore) < 0) {
			// C30002=签名已超时，请重试!
			return ResultData.fail("C30002");
		}

		String[] infos = {timeStamp, TG_SALT};
		StringBuilder strBuilder = new StringBuilder();
		for (Object info : infos) {
			strBuilder.append(info);
		}
		//对规则字符串sha1签名
		String signTemp = sha1(strBuilder.toString());
		if (!signTemp.equals(sign)) {
			// C30003=签名错误!
			return ResultData.fail("C30003");
		}
		return ResultData.success();
	}


	/**
	 * 得到输入字符串的SHA1哈希字符串
	 *
	 * @param inputString 输入字符串
	 * @return 返回SHA1哈希字符串
	 */
	public String sha1(String inputString) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(inputString.getBytes("UTF-8"));
			byte[] messageDigest = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte message : messageDigest) {
				String shaHex = Integer.toHexString(message & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString().toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
