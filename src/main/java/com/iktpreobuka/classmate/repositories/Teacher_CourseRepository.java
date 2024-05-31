package com.iktpreobuka.classmate.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.Teacher_Course;

@Repository
public interface Teacher_CourseRepository extends CrudRepository<Teacher_Course, Long> {
	Boolean existsByTeacherAndCourse(TeacherEntity teacher, CourseEntity course);

	Teacher_Course findByTeacherAndCourse(TeacherEntity teacher, CourseEntity course);

	List<Teacher_Course> findByTeacher(TeacherEntity teacher);
}
