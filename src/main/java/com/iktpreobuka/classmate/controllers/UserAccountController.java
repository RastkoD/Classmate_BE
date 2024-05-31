package com.iktpreobuka.classmate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.entities.UserAccountEntity;
import com.iktpreobuka.classmate.security.Views;
import com.iktpreobuka.classmate.services.UserService;


@RestController
@RequestMapping(value = "/api/userAccounts")
@CrossOrigin(origins = "*")
public class UserAccountController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all/admin")
	@JsonView(Views.AdminView.class)
	public List<UserAccountEntity> getAllUsersForAdmin() {
		return userService.getAllUsers();
	}
	
	// nije realno ali zbog views
	@GetMapping("/all/student")
	@JsonView(Views.StudentView.class)
	public List<UserAccountEntity> getAllUsersForStudent() {
		return userService.getAllUsers();
	}
	
	
	
}
