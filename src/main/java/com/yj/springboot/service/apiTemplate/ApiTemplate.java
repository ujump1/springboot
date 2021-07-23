//package com.yj.springboot.service.apiTemplate;
//
//import com.yj.springboot.service.utils.JsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.Map;
//
///**
// * 实现功能：
// * 平台调用API服务的客户端工具
// *
// * @author 王锦光(wangj)
// * @version 1.0.00      2017-03-27 10:07
// */
//public class ApiTemplate {
//
//    private static final Logger LOG = LoggerFactory.getLogger(ApiTemplate.class);
//
//    private final RestTemplate loadBalancedRestTemplate;
//
//    private final RestTemplate urlRestTemplate;
//
//    public ApiTemplate(RestTemplate loadBalancedRestTemplate, RestTemplate urlRestTemplate) {
//        this.loadBalancedRestTemplate = loadBalancedRestTemplate;
//        this.urlRestTemplate = urlRestTemplate;
//    }
//
//    public <T> T getByAppModuleCode(String appModuleCode, String path, Class<T> clz) {
//        return getByAppModuleCode(appModuleCode, path, clz, null);
//    }
//
//    public <T> T getByAppModuleCode(String appModuleCode, String path, ParameterizedTypeReference<T> responseType) {
//        return getByAppModuleCode(appModuleCode, path, responseType, null);
//    }
//
//    public <T> T getByAppModuleCode(String appModuleCode, String path, Class<T> clz, Map<String, String> params) {
//        String url = getAppModuleUrl(appModuleCode, path);
//        return getExecute(url, params, clz, true);
//    }
//
//    public <T> T getByAppModuleCode(String appModuleCode, String path, ParameterizedTypeReference<T> responseType, Map<String, String> params) {
//        String url = getAppModuleUrl(appModuleCode, path);
//        return getExecute(url, params, responseType, true);
//    }
//
//    public <T> T postByAppModuleCode(String appModuleCode, String path, Class<T> clz) {
//        return postByAppModuleCode(appModuleCode, path, clz, null);
//    }
//
//    public <T> T postByAppModuleCode(String appModuleCode, String path, ParameterizedTypeReference<T> responseType) {
//        return postByAppModuleCode(appModuleCode, path, responseType, null);
//    }
//
//    public <T> T postByAppModuleCode(String appModuleCode, String path, Class<T> clz, Object params) {
//        String url = getAppModuleUrl(appModuleCode, path);
//        return postExecute(url, new HttpEntity<>(params, getHttpHeaders()), clz, true);
//    }
//
//    public <T> T postByAppModuleCode(String appModuleCode, String path, ParameterizedTypeReference<T> responseType, Object params) {
//        String url = getAppModuleUrl(appModuleCode, path);
//        return postExecute(url, new HttpEntity<>(params, getHttpHeaders()), responseType, true);
//    }
//
//    public <T> T uploadFileByAppModuleCode(String appModuleCode, String path, ParameterizedTypeReference<T> responseType, Object params) {
//        String url = getAppModuleUrl(appModuleCode, path);
//        HttpHeaders headers = getHttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
//        return postExecute(url, new HttpEntity<>(params, headers), responseType, true);
//    }
//
//    public <T> T uploadFileByUrl(String url, ParameterizedTypeReference<T> responseType, Object params) {
//        HttpHeaders headers = getHttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
//        return postExecute(url, new HttpEntity<>(params, headers), responseType, false);
//    }
//
//    public <T> T postByUrl(String url, ParameterizedTypeReference<T> responseType) {
//        return postExecute(url, new HttpEntity<>(null, getHttpHeaders()), responseType, false);
//    }
//
//    public <T> T postByUrl(String url, ParameterizedTypeReference<T> responseType, Object params) {
//        return postExecute(url, new HttpEntity<>(params, getHttpHeaders()), responseType, false);
//    }
//
//    public <T> T postByUrl(String url, Class<T> clz) {
//        return postExecute(url, new HttpEntity<>(null, getHttpHeaders()), clz, false);
//    }
//
//    public <T> T postByUrl(String url, Class<T> clz, Object params) {
//        return postExecute(url, new HttpEntity<>(params, getHttpHeaders()), clz, false);
//    }
//
//    public void deleteByAppModuleCode(String appModuleCode, String path, String id) {
//        loadBalancedRestTemplate.delete(getAppModuleUrl(appModuleCode, path), id);
//    }
//
//    public <T> T getByUrl(String url, Class<T> clz) {
//        return this.getByUrl(url, clz, null);
//    }
//
//    public <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType) {
//        return this.getByUrl(url, responseType, null);
//    }
//
//    public <T> T getByUrl(String url, Class<T> clz, Map<String, String> params) {
//        return getExecute(url, params, clz, false);
//    }
//
//    public <T> T getByUrl(String url, ParameterizedTypeReference<T> responseType, Map<String, String> params) {
//        return getExecute(url, params, responseType, false);
//    }
//
//    private String getAppModuleUrl(String appModuleCode, String path) {
//        String url = "http://" + appModuleCode;
//        if (path.startsWith("/")) {
//            url = url + path;
//        } else {
//            url = url + "/" + path;
//        }
//        return url;
//    }
//
//    private <T> T postExecute(String url, HttpEntity<Object> requestEntity, ParameterizedTypeReference<T> responseType, boolean isBalanced) {
////        log.info("ApiTemplate post 请求，url:{},params:{}", url, JsonUtils.toJson(requestEntity.getBody()));
//        ResponseEntity<T> result;
//        if (isBalanced) {
//            result = loadBalancedRestTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
//        } else {
//            result = urlRestTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
//        }
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate post 请求完成，httpStatus:{}", result.getStatusCode());
//        }
//        return result.getBody();
//    }
//
//    private <T> T postExecute(String url, HttpEntity<Object> requestEntity, Class<T> clz, boolean isBalanced) {
////        log.info("ApiTemplate post 请求，url:{},params:{}", url, JsonUtils.toJson(requestEntity.getBody()));
//        ResponseEntity<T> result;
//        if (isBalanced) {
//            result = loadBalancedRestTemplate.exchange(url, HttpMethod.POST, requestEntity, clz);
//        } else {
//            result = urlRestTemplate.exchange(url, HttpMethod.POST, requestEntity, clz);
//        }
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate post 请求完成，httpStatus:{}", result.getStatusCode());
//        }
//        return result.getBody();
//    }
//
//    private <T> T getExecute(String url, Map<String, String> params, Class<T> clz, boolean isBalanced) {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate get 请求，url:{},params:{}", url, JsonUtils.toJson(params));
//        }
//        ResponseEntity<T> result;
//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
//        if (!CollectionUtils.isEmpty(params)) {
//            params.forEach(builder::queryParam);
//        }
//        url = builder.build().encode().toString();
//        if (isBalanced) {
//            if (CollectionUtils.isEmpty(params)) {
//                result = loadBalancedRestTemplate.exchange(url, HttpMethod.GET, httpEntity, clz);
//            } else {
//                result = loadBalancedRestTemplate.exchange(url, HttpMethod.GET, httpEntity, clz, params);
//            }
//
//        } else {
//            if (CollectionUtils.isEmpty(params)) {
//                result = urlRestTemplate.exchange(url, HttpMethod.GET, httpEntity, clz);
//            } else {
//                result = urlRestTemplate.exchange(url, HttpMethod.GET, httpEntity, clz, params);
//            }
//        }
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate post 请求完成，httpStatus:{}", result.getStatusCode());
//        }
//        return result.getBody();
//    }
//
//    private <T> T getExecute(String url, Map<String, String> params, ParameterizedTypeReference<T> responseType, boolean isBalanced) {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate get 请求，url:{},params:{}", url, JsonUtils.toJson(params));
//        }
//        ResponseEntity<T> result;
//        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, getHttpHeaders());
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
//        if (!CollectionUtils.isEmpty(params)) {
//            params.forEach(builder::queryParam);
//        }
//        url = builder.build().encode().toString();
//        if (isBalanced) {
//            if (CollectionUtils.isEmpty(params)) {
//                result = loadBalancedRestTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
//            } else {
//                result = loadBalancedRestTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, params);
//            }
//
//        } else {
//            if (CollectionUtils.isEmpty(params)) {
//                result = urlRestTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
//            } else {
//                result = urlRestTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, params);
//            }
//        }
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("ApiTemplate post 请求完成，httpStatus:{}", result.getStatusCode());
//        }
//        return result.getBody();
//    }
//
//    private HttpHeaders getHttpHeaders() {
//        //headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
//        // todo 从可传播的线程全局变量中读取并设置到请求header中
//        return headers;
//    }
//}
