package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.Teacher_Course;
import com.iktpreobuka.classmate.entities.Teacher_Course_Class;

@Repository
public interface Teacher_Course_ClassRepository extends CrudRepository<Teacher_Course_Class, Long> {

	//boolean existsByClassAndTeacherCourse(ClassEntity assignedClass, Teacher_Course teacherCourse);

	//Object findByClassAndTeacherCourse(ClassEntity assignedClass, Teacher_Course teacherCourse);

	//Teacher_Course_Class findByStudentAndTeacherCourse(ClassEntity assignedClass, Teacher_Course teacherCourse);

}
