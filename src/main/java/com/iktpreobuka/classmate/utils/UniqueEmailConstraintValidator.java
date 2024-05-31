package com.iktpreobuka.classmate.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.iktpreobuka.classmate.repositories.GuardianRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String>{
	
	@Autowired
	private GuardianRepository guardianRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(guardianRepository.existsByEmail(value)) {
			return false;
		}
		
		return true;
	}

}
