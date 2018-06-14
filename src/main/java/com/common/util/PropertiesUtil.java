package com.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {
	
	private static final Properties PROPERTIES = new Properties();
	/**
	 * 这个也可以使用web环境下的路径 
	 */
	static {
		try {
			 InputStream input= PropertiesUtil.class.getClassLoader().getResourceAsStream("a.properties");
			PROPERTIES.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return PROPERTIES.getProperty(key);
	}
	
	/**
	 * 测试成功  在web环境中使用
	 * @param file 在resource路径下
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String getValue(String file,String key) throws IOException {
		 Properties properties = PropertiesLoaderUtils.loadAllProperties(file);  
		 return properties.getProperty(key);
	}
}
