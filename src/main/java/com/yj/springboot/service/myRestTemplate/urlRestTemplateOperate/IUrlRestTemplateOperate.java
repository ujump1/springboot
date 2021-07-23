package com.yj.springboot.service.myRestTemplate.urlRestTemplateOperate;

import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

/**
 * url请求接口类
 * @author YJ
 * @version 1.0.1 2021/7/23
 */
public  interface IUrlRestTemplateOperate {

	// get
	 <T> T getByUrl(String url, Class<T> clz);

	 <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType);

	 <T> T getByUrl(String url, Class<T> clz, Map<String, String> params);

	 <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType, Map<String, String> params);


	// post
	 <T> T postExecute(String url, ParameterizedTypeReference<T> responseType);

	 <T> T postExecute(String url, ParameterizedTypeReference<T> responseType, Object params);

	 <T> T postExecute(String url, Map<String, String> urlParams, ParameterizedTypeReference<T> responseType);

	 <T> T postExecute(String url, Map<String, String> urlParams, ParameterizedTypeReference<T> responseType, Object params);

	// 带其他header的待完善
	// 举个例子把
	public <T> T uploadFile(String url, ParameterizedTypeReference<T> responseType, Object params);


}
