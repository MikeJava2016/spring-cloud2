package com.user.service;

import com.user.entity.Student;

public interface StudentService {
	
	 Student selectByPrimaryKey(String id);

	Student insert(Student student);
	 
}
