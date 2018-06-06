package com.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.convert.XssJsonConvert;
@SpringBootApplication
@MapperScan(value ="com.user.dao")
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
}
