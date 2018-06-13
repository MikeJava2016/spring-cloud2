package com.user.dao;

import com.user.entity.OrderDetail;

public interface OrderDetailMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);
    /**
     * one to one关联查询
     * @param id
     * @return
     */
    OrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}