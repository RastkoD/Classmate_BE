package com.iktpreobuka.classmate.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.UserRoleEntity;
import com.iktpreobuka.classmate.entities.enums.RoleEnum;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {

	Optional<UserRoleEntity> findByRoleName(RoleEnum roleName);
	
}
