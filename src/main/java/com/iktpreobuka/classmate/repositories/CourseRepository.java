package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.CourseEntity;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

}
