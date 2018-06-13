package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
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
	public ApiResponse<?> getOrdertail(@PathVariable int id) {
		OrderDetail orderDetail = orderDetailService.selectByPrimaryKey(id);
		return ApiResponse.successResponse(orderDetail);
		
	}
	/**
	 * 测试通过
	 * @param userid
	 * @return
	 */
	@RequestMapping("/get/user/{userid}")
	public ApiResponse<?> getUser(@PathVariable int userid) {
		 return ApiResponse.successResponse(userService.selectByPrimaryKey(userid));
	}
}
