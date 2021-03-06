package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResponse;
import com.common.util.BaseController;
import com.user.entity.Course;
import com.user.entity.Student;
import com.user.entity.StudentCourse;
import com.user.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentCotroller extends BaseController {
	
	@Autowired private StudentService studentService;
	
	/**
	 * 多对多查询
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/get/{sid}",method = RequestMethod.GET)
	public ApiResponse<?> get(@PathVariable String sid) {
		Student student = studentService.selectByPrimaryKey(sid);
		return ApiResponse.successResponse(student);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST )
	public ApiResponse<?> insert(@RequestBody Student student) {
	    studentService.insert(student);
		logger.info("insert  = " + student.toString());
		return ApiResponse.successResponse(student);
	}
}
