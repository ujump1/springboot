package com.yj.springboot;


import com.yj.springboot.service.myRestTemplate.HttpClientPoolProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSwagger2
// 用了自动装配，这里就不用注解了，这里
// 这里相当于在配置类中加@EnableJpaRepositories加spring-factories中的配置，这个注解只有一个生效把
//@EnableJpaRepositories(basePackages = {"com.yj.**.dao"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
