package com.user.dao;

import java.util.List;

import com.user.entity.User;

public interface CommonMapper<T> {
	List<T> select();
	
	int put(T t);
	
	int updateById(T t);
	
	int deleteById(int id);
}
