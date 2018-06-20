package com.user.service;

import java.util.List;

import com.user.entity.User;
public interface UserService {

	List<User> listUser();

	User put(String id, User user);

	void remove(String id);

	User get(String id);

	User update(String id, User u);

	User selectByPrimaryKey(String userid);

}
