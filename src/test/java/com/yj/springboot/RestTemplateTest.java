package com.yj.springboot;

import com.yj.springboot.service.myRestTemplate.urlRestTemplateOperate.UrlRestTemplateOperate;
import com.yj.springboot.service.responseModel.ResultData;
import com.yj.springboot.service.utils.JsonUtils;
import com.yj.springboot.service.vo.BillData;
import com.yj.springboot.service.vo.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestTemplateTest extends BaseTest {

	@Autowired
	private UrlRestTemplateOperate urlRestTemplateOperate;

	@Test
	public void test(){
		String url = "http://10.200.28.10:20000/dmp/cgzb/bill/queryByPage";
		Map<String, String> param = new HashMap<>();
		param.put("billNos", "WDTCK2106250063,WDTCK2106250049");
		param.put("billType", "11");
		param.put("channel","2");
		Result<List<BillData>> resultData = urlRestTemplateOperate.getByUrl(url,new ParameterizedTypeReference<Result<List<BillData>>>() {},param);
		System.out.println(JsonUtils.toJson(resultData));
	}


}
