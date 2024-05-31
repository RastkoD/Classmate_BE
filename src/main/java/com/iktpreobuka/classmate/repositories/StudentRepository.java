package com.iktpreobuka.classmate.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

	boolean existsByUsername(String username);

	StudentEntity findByUsername(String username);

	List<StudentEntity> findByAttendingClass(ClassEntity clazz);

}
