package com.user.dao;

import com.user.entity.Items;

public interface ItemsMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(Items record);

    int insertSelective(Items record);

    Items selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Items record);

}