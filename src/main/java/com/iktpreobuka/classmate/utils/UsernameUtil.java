package com.iktpreobuka.classmate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.repositories.GuardianRepository;
import com.iktpreobuka.classmate.repositories.StudentRepository;
import com.iktpreobuka.classmate.repositories.TeacherRepository;

@Component
public class UsernameUtil {
	
	@Autowired
	private GuardianRepository guardianRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	public String generateUniqueUsername(String baseUsername) {
		String username = baseUsername;
		int count = 1;
		
		while (guardianRepository.existsByUsername(username) ||
				studentRepository.existsByUsername(username) ||
				teacherRepository.existsByUsername(username)) {
			username = baseUsername + count;
			count++;
		}
		
		return username;
	}
}
