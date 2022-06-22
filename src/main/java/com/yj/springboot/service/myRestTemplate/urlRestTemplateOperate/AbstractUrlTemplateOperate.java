package com.yj.springboot.service.myRestTemplate.urlRestTemplateOperate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * url请求接口实现基类
 * @author YJ
 * @version 1.0.1 2021/7/23
 */
public abstract class AbstractUrlTemplateOperate implements IUrlRestTemplateOperate {

	protected abstract RestTemplate getRestTemplate();

	// get
	public <T> T getByUrl(String url, Class<T> clz) {
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
		return getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, clz).getBody();
	}

	public <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType) {
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
		return getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, responseType).getBody();
	}

	public <T> T getByUrl(String url, Class<T> clz, Map<String, String> params) {
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (!CollectionUtils.isEmpty(params)) {
			params.forEach(builder::queryParam);
		}
		url = builder.build().encode().toString();
		return getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, clz).getBody();
	}

	// 	Result<List<BillData>> result = urlRestTemplateOperate.getByUrl(url+billMethod,new ParameterizedTypeReference<Result<List<BillData>>>() {},param);
	public <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType, Map<String, String> params) {
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (!CollectionUtils.isEmpty(params)) {
			params.forEach(builder::queryParam);
		}
		url = builder.build().encode().toString();
		return getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, responseType).getBody();
	}


	// post
	public <T> T postExecute(String url, ParameterizedTypeReference<T> responseType) {
		return getRestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(null, getHttpHeaders()), responseType).getBody();
	}

	public <T> T postExecute(String url, ParameterizedTypeReference<T> responseType, Object params) {
		return getRestTemplate().exchange(url, HttpMethod.POST,new HttpEntity<>(params, getHttpHeaders()), responseType).getBody();
	}

	public <T> T postExecute(String url,Map<String, String> urlParams, ParameterizedTypeReference<T> responseType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (!CollectionUtils.isEmpty(urlParams)) {
			urlParams.forEach(builder::queryParam);
		}
		url = builder.build().encode().toString();
		return getRestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(null, getHttpHeaders()), responseType).getBody();
	}

	public <T> T postExecute(String url, Map<String, String> urlParams, ParameterizedTypeReference<T> responseType, Object params) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (!CollectionUtils.isEmpty(urlParams)) {
			urlParams.forEach(builder::queryParam);
		}
		url = builder.build().encode().toString();
		return getRestTemplate().exchange(url, HttpMethod.POST,new HttpEntity<>(params, getHttpHeaders()), responseType).getBody();
	}


	// 带其他header的待完善
	// 举个例子把
	public <T> T uploadFile(String url, ParameterizedTypeReference<T> responseType, Object params) {
		HttpHeaders headers = getHttpHeaders();
		headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
		return getRestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(params, headers), responseType).getBody();
	}



	// 设置http头
	public HttpHeaders getHttpHeaders() {
		//headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;UTF-8"));

		return headers;
	}
}
