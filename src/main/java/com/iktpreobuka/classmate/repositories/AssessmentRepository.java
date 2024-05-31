package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.AssessmentEntity;

@Repository
public interface AssessmentRepository extends CrudRepository<AssessmentEntity, Long> {

}
