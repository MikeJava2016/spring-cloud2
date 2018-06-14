package com.user;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.convert.XssJsonConvert;
@SpringBootApplication
@MapperScan(value ="com.user.dao")
@EnableAsync
@EnableScheduling
public class SpringbootMybatisApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

	/**
	 * 注入防止xss攻击的json转换器
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		XssJsonConvert xssJsonConvert = new XssJsonConvert();
		ObjectMapper objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		xssJsonConvert.setObjectMapper(objectMapper);
		return xssJsonConvert;
	}
	
	/**
	 * 禁止使用https
	 * @return
	 */
	//@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	   TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
	     @Override
	     protected void postProcessContext(Context context) {
	       SecurityConstraint securityConstraint = new SecurityConstraint();
	       securityConstraint.setUserConstraint("CONFIDENTIAL");
	       SecurityCollection collection = new SecurityCollection();
	       collection.addPattern("/*");
	       securityConstraint.addCollection(collection);
	       context.addConstraint(securityConstraint);
	     }
	   };
	   tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
	   return tomcat;
	 }
	 
	 
	 private Connector initiateHttpConnector() {
	   Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	   connector.setScheme("http");
	   connector.setPort(8080);
	   connector.setSecure(false);
	   connector.setRedirectPort(8443);
	   return connector;
	 }

}
