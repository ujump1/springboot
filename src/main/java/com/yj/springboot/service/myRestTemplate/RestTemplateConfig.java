package com.yj.springboot.service.myRestTemplate;


import com.yj.springboot.service.apiTemplate.SeiRestTemplateErrorHandle;
import com.yj.springboot.service.utils.LogUtil;
import okhttp3.OkHttpClient;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 自定义restTemplate
 */
@Configuration
@EnableConfigurationProperties({HttpClientPoolProperties.class})
public class RestTemplateConfig {

	@Autowired
	private HttpClientPoolProperties poolProperties;

	@Primary
	@Bean("myUrlTemplate")
	public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
		return createRestTemplate(requestFactory);
	}

	/**
	 * 默认的实现,注意同一个类型的构造方法名不要一样哈
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplateDefault(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean("myUrlTemplate2")
	public RestTemplate restTemplateOfBuilder(@Qualifier("MyClientHttpRequestFactory1") ClientHttpRequestFactory requestFactory) {
		return createRestTemplate(requestFactory);
	}

	// 当有多个bean被其他的构造函数注入时，如果有	@Primary那就会用这个，没有的话就需要使用@Qualifier来注明
	@Primary
	@Bean(name = {"MyClientHttpRequestFactory"})
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		if (this.poolProperties.getMaxTotalConnect() <= 0) {
			throw new IllegalArgumentException("invalid maxTotalConnection: " + this.poolProperties.getMaxTotalConnect());
		} else if (this.poolProperties.getMaxConnectPerRoute() <= 0) {
			throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + this.poolProperties.getMaxConnectPerRoute());
		} else {
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(this.apacheHttpClient());
			clientHttpRequestFactory.setConnectTimeout(this.poolProperties.getConnectTimeout());
			clientHttpRequestFactory.setReadTimeout(this.poolProperties.getReadTimeout());
			clientHttpRequestFactory.setConnectionRequestTimeout(this.poolProperties.getConnectionRequestTimout());
			clientHttpRequestFactory.setBufferRequestBody(false);
			return clientHttpRequestFactory;
		}
	}
	@Bean(name = {"MyClientHttpRequestFactory1"})
	public ClientHttpRequestFactory clientHttpRequestFactory1() {
		if (this.poolProperties.getMaxTotalConnect() <= 0) {
			throw new IllegalArgumentException("invalid maxTotalConnection: " + this.poolProperties.getMaxTotalConnect());
		} else if (this.poolProperties.getMaxConnectPerRoute() <= 0) {
			throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + this.poolProperties.getMaxConnectPerRoute());
		} else {
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(this.apacheHttpClient());
			clientHttpRequestFactory.setConnectTimeout(this.poolProperties.getConnectTimeout());
			clientHttpRequestFactory.setReadTimeout(this.poolProperties.getReadTimeout());
			clientHttpRequestFactory.setConnectionRequestTimeout(this.poolProperties.getConnectionRequestTimout());
			clientHttpRequestFactory.setBufferRequestBody(false);
			return clientHttpRequestFactory;
		}
	}


	@Bean
	public HttpClient apacheHttpClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try {
			SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, (arg0, arg1) -> {
				return true;
			}).build();
			httpClientBuilder.setSSLContext(sslContext);
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslConnectionSocketFactory).build();
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			poolingHttpClientConnectionManager.setMaxTotal(this.poolProperties.getMaxTotalConnect());
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(this.poolProperties.getMaxConnectPerRoute());
			httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(this.poolProperties.getRetryTimes(), true));
			List<Header> headers = this.getDefaultHeaders();
			httpClientBuilder.setDefaultHeaders(headers);
			httpClientBuilder.setKeepAliveStrategy(this.connectionKeepAliveStrategy());
			return httpClientBuilder.build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var8) {
			LogUtil.error("初始化HTTP连接池出错", var8);
			return null;
		}
	}

	/**
	 * 配置长连接保持策略
	 */
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return (response, context) -> {
			// Honor 'keep-alive' header
			HeaderElementIterator it = new BasicHeaderElementIterator(
					response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();
				if (value != null && "timeout".equalsIgnoreCase(param)) {
					try {
						return Long.parseLong(value) * 1000;
					} catch (NumberFormatException e) {
						LogUtil.error("解析长连接过期时间异常", e);
					}
				}
			}
			HttpHost target = (HttpHost) context.getAttribute(
					HttpClientContext.HTTP_TARGET_HOST);
			//如果请求目标地址,单独配置了长连接保持时间,使用该配置
			Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(poolProperties.getKeepAliveTargetHost()).orElseGet(HashMap::new)
					.entrySet().stream().filter(
							e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();
			//否则使用默认长连接保持时间
			return any.map(en -> en.getValue() * 1000L).orElse(poolProperties.getKeepAliveTime() * 1000L);
		};
	}


	/**
	 * 设置请求头
	 */
	private List<Header> getDefaultHeaders() {
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		return headers;
	}

	private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);

		//我们采用RestTemplate内部的MessageConverter
		//重新设置StringHttpMessageConverter字符集，解决中文乱码问题
		modifyDefaultCharset(restTemplate);

		//设置错误处理器
		restTemplate.setErrorHandler(new SeiRestTemplateErrorHandle());

		return restTemplate;
	}

	/**
	 * 修改默认的字符集类型为utf-8
	 */
	private void modifyDefaultCharset(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
		HttpMessageConverter<?> converterTarget = null;
		for (HttpMessageConverter<?> item : converterList) {
			if (StringHttpMessageConverter.class == item.getClass()) {
				converterTarget = item;
				break;
			}
		}
		if (null != converterTarget) {
			converterList.remove(converterTarget);
		}
		Charset defaultCharset = Charset.forName(poolProperties.getCharset());
		converterList.add(1, new StringHttpMessageConverter(defaultCharset));
	}


	/****************OkHttp使用****************/
	@Value("${OkHttpRestTemplate.connect-timeout:10000}")
	private int connectTimeout; // 连接超时时间
	@Value("${OkHttpRestTemplate.read-timeout:20000}")
	private int readTimeout;  // 读取超时时间

	@Bean("OkHttpUrlRestTemplate")
	public RestTemplate creatEleCloudTemplate() {
		OkHttpClient okHttpClient = new OkHttpClient();
		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setErrorHandler(new SeiRestTemplateErrorHandle());
		return restTemplate;
	}

}
