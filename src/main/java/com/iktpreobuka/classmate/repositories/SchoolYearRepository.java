package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.SchoolYearEntity;

@Repository
public interface SchoolYearRepository extends CrudRepository<SchoolYearEntity, Long> {

	SchoolYearEntity findBySchoolYearName(String schoolYearName);
	
}
