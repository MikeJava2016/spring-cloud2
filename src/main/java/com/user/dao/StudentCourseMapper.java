package com.user.dao;

import java.util.List;

import com.user.entity.StudentCourse;

public interface StudentCourseMapper {
    int deleteByPrimaryKey(StudentCourse key);

    int insert(StudentCourse record);

    int insertSelective(StudentCourse record);
    
    List<StudentCourse> getCourseByStudentId(String studentId);
    
    //List<StudentCourse> getCourseByCourseId(int courseId);
}