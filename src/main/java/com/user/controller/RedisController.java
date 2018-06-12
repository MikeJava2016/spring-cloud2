package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.ApiResponse;
import com.user.utils.RedisHandle;

//@RestController
@RequestMapping("/redis")
public class RedisController {
	 @Autowired
     private RedisHandle rs;
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ApiResponse<?> postUser() {
		rs.set("redis", "redis");
		return ApiResponse.successResponse(rs.get("redis"));
	}
	
	
	
	
}
