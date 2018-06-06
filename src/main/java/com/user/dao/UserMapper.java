package com.user.dao;

import com.user.entity.User;

public interface UserMapper extends CommonMapper<User>{
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}