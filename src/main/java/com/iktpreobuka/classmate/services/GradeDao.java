package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.classmate.entities.GradeEntity;

public interface GradeDao {
	
	List<GradeEntity> getAllGrades();
	
	Optional<GradeEntity> getGradeById(Long gradeId);
	
	GradeEntity createGrade(GradeEntity grade);
	
	GradeEntity updateGrade(Long gradeId, GradeEntity updatedGrade);
	
	void deleteGrade(Long gradeId);
}
