package com.user.convert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.user.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 自定义转换器 过滤xss攻击
 * @author hu
 *
 */
public class XssJsonConvert extends MappingJackson2HttpMessageConverter {
	/*public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		XssJsonConvert xssJsonConvert = new XssJsonConvert();
		map.put("a", "<a>a</a>");
		List<String>list = new ArrayList<>();
		list.add("<a>\"a\"</a>--java");
		map.put("b", list);
		map.put("c", new User().setAge("13").setPassword("1342").setId(100));
		
		//map.put("a", map);
		System.out.println(xssJsonConvert.convert(JSONObject.fromObject(map).toString()));
		
	}*/
	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(type, contextClass);
		Object object = this.readJavaType(javaType,inputMessage);
		String json = getObjectMapper().writeValueAsString(object);
		json = convert(json);
		Object result = super.getObjectMapper().readValue(json, javaType);
		return result;
	}
	//转换对象
	public String convert(String json) {
		//等于空或者null 不处理
		if (StringUtils.isEmpty(json)) {
			return json;
		}
		try {
			//判断是否是json对象
			if (json.startsWith("{")) {
				JSONObject jsonObject = JSONObject.fromObject(json);
				JSONObject res = handleJSONObject(jsonObject);
				return res.toString();
			}
			//判断是否是json数组
			else if (json.startsWith("[")) {
				JSONArray jsonArray = JSONArray.fromObject(json);
				JSONArray res = handleJSONArray(jsonArray);
				return res.toString();
			}else {
				return json;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "{}";
		}
	}
	
	/**
	 * 处理jsonArray对象
	 * @param jsonArray
	 * @return
	 */
	private JSONArray handleJSONArray(JSONArray jsonArray) {
		//空值判断
		if (null == jsonArray || jsonArray.isEmpty() || jsonArray.size() ==0) {
			return jsonArray;
		}
		//遍历
		for(int i = 0, size = jsonArray.size(); i<size;i++ ) {
			Object obj = jsonArray.get(i);
			//判断是数组对象
			if (obj instanceof List) {
				handleJSONArray((JSONArray)obj);
			}
			//判断是对象
			else if (obj instanceof Map) {
				handleJSONObject((JSONObject)obj);
			}
			//判断是简单字符串
			else if (obj instanceof String) {
				jsonArray.set(i, handleStr((String)obj));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 处理jsonObject对象
	 * @param jsonObject
	 * @return
	 */
	private JSONObject handleJSONObject(JSONObject jsonObject) {
		@SuppressWarnings("rawtypes")
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			//获取对象的key
			String key = (String) iterator.next();
			//获取对象的值
			Object object = jsonObject.get(key);
			//判断数组类型
			if (object instanceof List) {
				jsonObject.put(key, handleJSONArray((JSONArray) object));
			}
			//判断是对象结构即Map结构
			else if (object instanceof Map) {
				jsonObject.put(key, handleJSONObject((JSONObject) object));
			}
			//简单字符串
			else if (object instanceof String) {
				jsonObject.put(key, handleStr((String)object));
			}
		}
		return jsonObject;
	}

	/**
	 * 处理字符串代码
	 * @param str
	 * @return
	 */
	private Object handleStr(String str) {
		return str.replace("&", "&amp")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"","&quot;")
				.replace("'","&#x27;")
				.replace("(","&#40;")
				.replace(")","&#41;")
				.replace("%","&#37;")
				.replace("+", "&#43;")
				.replace("\\", "&#92;");
				
	}

	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
		try {
			return super.getObjectMapper().readValue(inputMessage.getBody(), javaType);
		} catch (Exception e) {
			throw new HttpMessageNotReadableException("Could not read json :"+ e.getMessage(),e);
		}
	}
}
