package com.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.BaseService;
import com.user.dao.OrderMapper;
import com.user.service.OrdersService;
@Service
@Transactional(readOnly = true)
public class OrdersServiceImpl extends BaseService implements OrdersService  {

	@Autowired private OrderMapper orderMapper;
	

}
