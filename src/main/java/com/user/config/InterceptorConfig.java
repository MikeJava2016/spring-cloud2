package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.common.util.TimeInterceptor;
/**
 * 拦截器的配置类
 * @author hu
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Autowired private WebConfig webConfig;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (webConfig.getTimeInterceptor()) registry.addInterceptor(new TimeInterceptor());
		super.addInterceptors(registry);
	}
}
