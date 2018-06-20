package com.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
import com.common.util.PropertiesUtil;
import com.github.pagehelper.PageHelper;
import com.user.entity.Order;
import com.user.entity.OrderDetail;
import com.user.service.OrderDetailService;
import com.user.service.OrdersService;
import com.user.service.UserService;

@RestController
@RequestMapping(value = "/demo")
public class DemoCotroller {
	
	@Autowired private OrderDetailService orderDetailService;
	
	@Autowired private OrdersService ordersService;
	
	@Autowired private UserService userService;
	
	/**
	 * 测试通过
	 * @param id
	 * @return
	 */
	@RequestMapping("/get/orderdetail/{id}")
	public ApiResponse<?> getOrdertail(@PathVariable String id) {
		OrderDetail orderDetail = orderDetailService.selectByPrimaryKey(id);
		System.out.println(PropertiesUtil.getValue("name"));
		try {
			System.out.println(PropertiesUtil.getValue("a.properties", "name"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ApiResponse.successResponse(orderDetail);
		
	}
	/**
	 * 测试通过
	 * @param userid
	 * @return
	 */
	@RequestMapping("/get/user/{userid}")
	public ApiResponse<?> getUser(@PathVariable String userid) {
		 PageHelper.startPage(1, 1);
		 return ApiResponse.successResponse(userService.selectByPrimaryKey(userid));
	}
}
