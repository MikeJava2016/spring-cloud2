package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
import com.user.entity.Course;
import com.user.entity.Student;
import com.user.entity.StudentCourse;
import com.user.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentCotroller {
	
	@Autowired private StudentService studentService;
	
	/**
	 * 多对多查询
	 * @param sid
	 * @return
	 */
	@RequestMapping("/get/{sid}")
	public ApiResponse<?> get(@PathVariable int sid) {
		Student student = studentService.selectByPrimaryKey(sid);
		return ApiResponse.successResponse(student);
	}
}
