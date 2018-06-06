package com.user.service;

import java.util.List;

import com.user.entity.User;

public interface UserService {

	List<User> listUser();

	void put(Integer id, User user);

	void remove(int id);

	User get(int id);

	void update(Integer id, User u);

}
