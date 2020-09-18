package com.yj.springboot.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 拦截
 * @author : YJ
 * @version : 1.0
 * @date : Created in 2020/8/18 14:53
 * @modified by :
 */
@Configuration
public class WebCorsConfig implements  WebMvcConfigurer {
    @Resource
    private SessionUserTokenInterceptor sessionUserTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionUserTokenInterceptor).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/static/**", "/view/** "))
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.OPTIONS.name(), HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                .allowCredentials(false).maxAge(3600);
    }

//    /**
//     * 解决跨域请求
//     *
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
//    }
};
