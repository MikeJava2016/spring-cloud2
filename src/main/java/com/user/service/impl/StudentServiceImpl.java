package com.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.StudentMapper;
import com.user.entity.Student;
import com.user.service.StudentService;
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

	@Autowired private StudentMapper studentMapper;
	@Override
	public Student selectByPrimaryKey(String id) {
		Student student = studentMapper.selectByPrimaryKey(id);
		return student;
	}
	
	@Transactional(readOnly = false)
	@Override
	public Student insert(Student student) {
		studentMapper.insert(student);
		return  student;
	}

}
