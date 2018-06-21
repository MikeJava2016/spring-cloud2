package com.user.dao;

import com.user.entity.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Student student);

    int insertSelective(Student student);

    Student selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Student student);

    int updateByPrimaryKey(Student student);
}