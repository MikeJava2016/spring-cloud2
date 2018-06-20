package com.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.OrderDetailMapper;
import com.user.entity.OrderDetail;
import com.user.service.OrderDetailService;
@Service
@Transactional(readOnly = true)
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired private OrderDetailMapper orderDetailMapper;

	@Override
	public OrderDetail selectByPrimaryKey(String id) {
		return orderDetailMapper.selectByPrimaryKey(id);
	}

}
