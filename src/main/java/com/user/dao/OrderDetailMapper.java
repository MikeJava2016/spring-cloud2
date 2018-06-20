package com.user.dao;

import java.util.List;

import com.user.entity.OrderDetail;

public interface OrderDetailMapper {

	int deleteByPrimaryKey(String id);

	int insert(OrderDetail record);

	int insertSelective(OrderDetail record);

	/**
	 * one to one关联查询
	 * 
	 * @param id
	 * @return
	 */
	OrderDetail selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OrderDetail record);

	int updateByPrimaryKey(OrderDetail record);

	List<OrderDetail> selectByOrderId(String orderId);
}