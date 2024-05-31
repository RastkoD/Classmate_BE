package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.AdminEntity;

@Repository
public interface AdminRepository extends CrudRepository<AdminEntity, Long> {

}
