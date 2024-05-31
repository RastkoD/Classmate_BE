package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.ClassEntity;

@Repository
public interface ClassRepository extends CrudRepository<ClassEntity, Long> {

}
