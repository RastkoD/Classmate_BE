package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.TermEntity;

public interface CourseDao {
	
	Iterable<CourseEntity> getAllCourses();
	
	CourseEntity getCourseById(Long courseId);
	
	CourseEntity createCourse(CourseEntity newCourse);
	
	CourseEntity updateCourse(Long courseId, CourseEntity updatedCourse);
	
	void deleteCourse(Long courseId);
	
	 List<CourseEntity> getCoursesByTermAndGrade(TermEntity termEntity, GradeEntity gradeEntity);


}
