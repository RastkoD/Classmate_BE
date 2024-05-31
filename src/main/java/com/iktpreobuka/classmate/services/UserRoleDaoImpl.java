package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.UserRoleEntity;
import com.iktpreobuka.classmate.repositories.UserRoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserRoleDaoImpl implements UserRoleDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		return (List<UserRoleEntity>) userRoleRepository.findAll();
	}

	@Override
	public Optional<UserRoleEntity> getUserRoleById(Long userRoleId) {
		return userRoleRepository.findById(userRoleId);
	}

	@Override
	public UserRoleEntity createUserRole(UserRoleEntity userRole) {
		return userRoleRepository.save(userRole);
	}

	// no need
	@Override
	public UserRoleEntity updateUserRole(Long userRoleId, UserRoleEntity updatedUserRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserRole(Long userRoleId) {
		userRoleRepository.deleteById(userRoleId);
		
	}
}
