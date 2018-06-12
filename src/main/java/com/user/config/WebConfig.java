package com.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@ConfigurationProperties(prefix = "project")
@Configuration
public class WebConfig {
	/**
	 * 是否开启时间拦截  测试接口运行时间
	 */
	private boolean timeInterceptor;

	public boolean getTimeInterceptor() {
		return timeInterceptor;
	}

	public void setTimeInterceptor(boolean timeInterceptor) {
		this.timeInterceptor = timeInterceptor;
	}
	
	
}
