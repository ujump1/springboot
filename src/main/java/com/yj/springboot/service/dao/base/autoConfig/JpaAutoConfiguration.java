package com.yj.springboot.service.dao.base.autoConfig;


import com.yj.springboot.entity.User;
import com.yj.springboot.service.dao.base.BaseDaoFactoryBean;
import com.yj.springboot.service.dao.base.impl.BaseEntityDaoImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2018/5/28 23:48
 */
@Configuration
@ConditionalOnBean({DataSource.class})
@ConditionalOnClass({BaseDaoFactoryBean.class, BaseEntityDaoImpl.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
//@EnableAspectJAutoProxy(proxyTargetClass = true)

@EnableJpaRepositories(basePackages = {"com.yj.**.dao"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
@EnableTransactionManagement
public class JpaAutoConfiguration {

	// 测试在spring-factories中配置了才能自动配置，不知道为什么
	@Bean
	public User user(){
		User user = new User();
		user.setName("123");
		return user;
	}
}
