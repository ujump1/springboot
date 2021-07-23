package com.yj.springboot.service.myRestTemplate.urlRestTemplateOperate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

;

@Component
public class UrlRestTemplateOperate extends AbstractUrlTemplateOperate {

	// 直接用产品urlRestTemplate
	@Autowired
	@Qualifier("myUrlTemplate")
	private RestTemplate restTemplate;

	@Override
	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
