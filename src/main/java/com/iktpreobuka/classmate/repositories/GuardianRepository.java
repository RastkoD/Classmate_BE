package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.GuardianEntity;

@Repository
public interface GuardianRepository extends CrudRepository<GuardianEntity, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String value);

	GuardianEntity findByUsername(String username);

}
