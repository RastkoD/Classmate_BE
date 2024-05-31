package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.classmate.entities.SchoolYearEntity;

public interface SchoolYearDao {
	
	List<SchoolYearEntity> getAllSchoolYears();
	
	Optional<SchoolYearEntity> getSchoolYearById(Long schoolYearId);
	
	SchoolYearEntity createSchoolYear(SchoolYearEntity schoolYear);
	
	SchoolYearEntity updateSchoolYear(Long schoolYearId, SchoolYearEntity updatedSchoolYear);
	
	void deleteSchoolYear(Long schoolYearId);
	
	SchoolYearEntity findSchoolYearByName(String schoolYearName);
}
