package com.iktpreobuka.classmate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.UserAccountEntity;
import com.iktpreobuka.classmate.repositories.GuardianRepository;
import com.iktpreobuka.classmate.repositories.StudentRepository;
import com.iktpreobuka.classmate.repositories.TeacherRepository;

@Service
public class UserService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private GuardianRepository guardianRepository;
	
	public List<UserAccountEntity> getAllUsers() {
		List<UserAccountEntity> allUsers = new ArrayList<>();
		
		Iterable<TeacherEntity> teachersIterable = teacherRepository.findAll();
        List<TeacherEntity> teachers = new ArrayList<>();
        teachersIterable.forEach(teachers::add);
        allUsers.addAll(teachers);

        Iterable<StudentEntity> studentsIterable = studentRepository.findAll();
        List<StudentEntity> students = new ArrayList<>();
        studentsIterable.forEach(students::add);
        allUsers.addAll(students);

        Iterable<GuardianEntity> guardiansIterable = guardianRepository.findAll();
        List<GuardianEntity> guardians = new ArrayList<>();
        guardiansIterable.forEach(guardians::add);
        allUsers.addAll(guardians);

        return allUsers;
	}
	
	/*
	public UserAccountEntity getUserByUsername(String username) {

	    TeacherEntity teacher = teacherRepository.findByUsername(username);
	    if (teacher != null) {
	        return teacher;
	    }

	    StudentEntity student = studentRepository.findByUsername(username);
	    if (student != null) {
	        return student;
	    }

	    GuardianEntity guardian = guardianRepository.findByUsername(username);
	    if (guardian != null) {
	        return guardian;
	    }

	    return null;
	}*/
}
