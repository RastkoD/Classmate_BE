package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.classmate.entities.UserRoleEntity;

public interface UserRoleDao {
	
	List<UserRoleEntity> getAllUserRoles();
	
	Optional<UserRoleEntity> getUserRoleById(Long userRoleId);
	
	UserRoleEntity createUserRole(UserRoleEntity userRole);
	
	UserRoleEntity updateUserRole(Long userRoleId, UserRoleEntity updatedUserRole);
	
	void deleteUserRole(Long userRoleId);
}
