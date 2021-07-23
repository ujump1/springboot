////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.yj.springboot.service.config;
//
//
//import com.yj.springboot.service.apiTemplate.ApiTemplate;
//import com.yj.springboot.service.apiTemplate.SeiRestTemplateErrorHandle;
//import com.yj.springboot.service.config.properties.HttpClientPoolProperties;
//import com.yj.springboot.service.utils.JsonUtils;
//import org.apache.http.Header;
//import org.apache.http.HeaderElement;
//import org.apache.http.HttpHost;
//import org.apache.http.client.HttpClient;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.ConnectionKeepAliveStrategy;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicHeaderElementIterator;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import java.nio.charset.Charset;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//
//@Configuration
//@ConditionalOnClass({RestTemplate.class, CloseableHttpClient.class})
//@EnableConfigurationProperties({HttpClientPoolProperties.class})
//public class DefaultAutoConfig {
//    private static final Logger LOG = LoggerFactory.getLogger(DefaultAutoConfig.class);
//    @Autowired
//    private HttpClientPoolProperties poolProperties;
//
//    public DefaultAutoConfig() {
//    }
//
//
//
//
//
//    @Bean(name = {"loadBalancedRestTemplate"})
//    @Primary
//    // @LoadBalanced
//    public RestTemplate loadBalancedRestTemplate(ClientHttpRequestFactory requestFactory) {
//        return this.createRestTemplate(requestFactory);
//    }
//
//    @Bean(name = {"urlRestTemplate"})
//    public RestTemplate urlRestTemplate(ClientHttpRequestFactory requestFactory) {
//        return this.createRestTemplate(requestFactory);
//    }
//
//    @Bean
//    public ApiTemplate apiTemplate(ClientHttpRequestFactory requestFactory) {
//        return new ApiTemplate(this.loadBalancedRestTemplate(requestFactory), this.urlRestTemplate(requestFactory));
//    }
//
//    @Bean(
//        name = {"clientHttpRequestFactory"}
//    )
//    public ClientHttpRequestFactory clientHttpRequestFactory() {
//        if (this.poolProperties.getMaxTotalConnect() <= 0) {
//            throw new IllegalArgumentException("invalid maxTotalConnection: " + this.poolProperties.getMaxTotalConnect());
//        } else if (this.poolProperties.getMaxConnectPerRoute() <= 0) {
//            throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + this.poolProperties.getMaxConnectPerRoute());
//        } else {
//            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(this.apacheHttpClient());
//            clientHttpRequestFactory.setConnectTimeout(this.poolProperties.getConnectTimeout());
//            clientHttpRequestFactory.setReadTimeout(this.poolProperties.getReadTimeout());
//            clientHttpRequestFactory.setConnectionRequestTimeout(this.poolProperties.getConnectionRequestTimout());
//            clientHttpRequestFactory.setBufferRequestBody(false);
//            return clientHttpRequestFactory;
//        }
//    }
//
//    @Bean
//    public HttpClient apacheHttpClient() {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//
//        try {
//            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, (arg0, arg1) -> {
//                return true;
//            }).build();
//            httpClientBuilder.setSSLContext(sslContext);
//            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
//            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                    .register("https", sslConnectionSocketFactory).build();
//            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//            poolingHttpClientConnectionManager.setMaxTotal(this.poolProperties.getMaxTotalConnect());
//            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(this.poolProperties.getMaxConnectPerRoute());
//            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
//            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(this.poolProperties.getRetryTimes(), true));
//            List<Header> headers = this.getDefaultHeaders();
//            httpClientBuilder.setDefaultHeaders(headers);
//            httpClientBuilder.setKeepAliveStrategy(this.connectionKeepAliveStrategy());
//            return httpClientBuilder.build();
//        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
//            LOG.error("初始化HTTP连接池出错", var8);
//            return null;
//        }
//    }
//
//    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
//        return (response, context) -> {
//            BasicHeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
//
//            while(true) {
//                String param;
//                String value;
//                do {
//                    do {
//                        if (!it.hasNext()) {
//                            HttpHost target = (HttpHost)context.getAttribute("http.target_host");
//                            Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(poolProperties.getKeepAliveTargetHost()).orElseGet(HashMap::new)
//                                    .entrySet().stream().filter(
//                                            e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();
//                            return (Long)any.map((en) -> {
//                                return (long)(Integer)en.getValue() * 1000L;
//                            }).orElse((long)this.poolProperties.getKeepAliveTime() * 1000L);
//                        }
//
//                        HeaderElement he = it.nextElement();
//                        if (LOG.isDebugEnabled()) {
//                            LOG.debug("HeaderElement:{}", JsonUtils.toJson(he));
//                        }
//
//                        param = he.getName();
//                        value = he.getValue();
//                    } while(value == null);
//                } while(!"timeout".equalsIgnoreCase(param));
//
//                try {
//                    return Long.parseLong(value) * 1000L;
//                } catch (NumberFormatException var8) {
//                    LOG.error("解析长连接过期时间异常", var8);
//                }
//            }
//        };
//    }
//
//    private List<Header> getDefaultHeaders() {
//        List<Header> headers = new ArrayList();
//        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
//        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
//        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
//        headers.add(new BasicHeader("Connection", "Keep-Alive"));
//        return headers;
//    }
//
//    private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
//        RestTemplate restTemplate = new RestTemplate(factory);
//        this.modifyDefaultCharset(restTemplate);
//        restTemplate.setErrorHandler(new SeiRestTemplateErrorHandle());
//        return restTemplate;
//    }
//
//    private void modifyDefaultCharset(RestTemplate restTemplate) {
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//        HttpMessageConverter<?> converterTarget = null;
//        Iterator var4 = converterList.iterator();
//
//        while(var4.hasNext()) {
//            HttpMessageConverter<?> item = (HttpMessageConverter)var4.next();
//            if (StringHttpMessageConverter.class == item.getClass()) {
//                converterTarget = item;
//                break;
//            }
//        }
//
//        if (null != converterTarget) {
//            converterList.remove(converterTarget);
//        }
//
//        Charset defaultCharset = Charset.forName(this.poolProperties.getCharset());
//        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
//    }
//}
