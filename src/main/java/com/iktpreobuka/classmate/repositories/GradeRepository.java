package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.enums.GradeEnum;

@Repository
public interface GradeRepository extends CrudRepository<GradeEntity, Long> {

	GradeEntity findByGrade(GradeEnum gradeName);

}
