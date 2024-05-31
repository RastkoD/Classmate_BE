package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;

public interface StudentDao {
	List<StudentEntity> getAllStudents();
	
	StudentEntity getStudentById(Long studentId);
	
	StudentEntity createStudent(StudentEntity newStudent);
	
	StudentEntity updateStudent(Long studentId, StudentEntity updatedStudent);
	
	void deleteStudent(Long studentId);

	boolean existsById(Long studentId);

	void save(StudentEntity student);

	List<StudentEntity> findByAttendingClass(ClassEntity clazz);

}
