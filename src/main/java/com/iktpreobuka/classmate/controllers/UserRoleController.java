package com.iktpreobuka.classmate.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.classmate.entities.UserRoleEntity;
import com.iktpreobuka.classmate.entities.dto.UserRoleDTO;
import com.iktpreobuka.classmate.services.UserRoleDao;

@RestController
@RequestMapping(value = "/api/userRoles")
@CrossOrigin(origins = "*")
public class UserRoleController {
	
	@Autowired
	private UserRoleDao userRoleDao;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userRoleDao.getAllUserRoles());
	}
	
	@GetMapping(value = "/{roleId}")
	public ResponseEntity<?> getRoleById(@PathVariable Long roleId) {
		
		Optional<UserRoleEntity> roleOptional = userRoleDao.getUserRoleById(roleId);
		
		if(roleOptional.isPresent()) {
			UserRoleEntity role = roleOptional.get();
			
			return ResponseEntity.ok(role);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Create New Role
	@PostMapping(value = "/create")
	public ResponseEntity<?> createRole(@RequestBody UserRoleDTO newRoleDTO) {
		UserRoleEntity newRole = new UserRoleEntity();
		
		newRole.setRoleName(newRoleDTO.getRoleName());
		
		UserRoleEntity createdRole = userRoleDao.createUserRole(newRole);
		
		if(createdRole != null) {
			return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create role.");
		}
	}

	// Delete Role
	@DeleteMapping(value = "/delete/{roleId}")
	public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
		userRoleDao.deleteUserRole(roleId);
		
		return ResponseEntity.ok("Role deleted successfully.");
	}
	
	// Get Users By RoleName
	
	// Change User's Role - /project/users/change/{id}/role/{role}
}
