package com.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.UserMapper;
import com.user.entity.User;
import com.user.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired	
	private UserMapper userMapper;
	@Override
	public List<User> listUser() {
		return userMapper.select();
	}
	
	@Transactional(readOnly = false)
	@Override
	public void put(Integer id, User user) {
		user.setId(id);
		userMapper.put(user);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void remove(int id) {
		userMapper.deleteById(id);
	}

	@Override
	public User get(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Transactional(readOnly = false)
	@Override
	public void update(Integer id, User u) {
		u.setId(id);
		userMapper.updateById(u);
		
	}

}
