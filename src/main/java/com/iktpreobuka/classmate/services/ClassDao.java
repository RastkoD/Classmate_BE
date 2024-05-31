package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.ClassEntity;

public interface ClassDao {
	List<ClassEntity> getAllClasses();
	
	ClassEntity getClassById(Long classId);
	
	ClassEntity createClass(ClassEntity newClass);
	
	ClassEntity updateClass(Long classId, ClassEntity updatedClass);
	
	void deleteClass(Long classId);

	boolean existsById(Long classId);

	//ClassEntity addCourseForClass(Long classId, Long courseId, Long teacherId);
}
