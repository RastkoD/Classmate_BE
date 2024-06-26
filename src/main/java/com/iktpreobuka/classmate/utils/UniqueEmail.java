package com.iktpreobuka.classmate.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface UniqueEmail {
	
	public String message() default "Email already in use.";
	
	public Class<?>[] groups() default{};
	
	public Class<? extends Payload>[] payload() default{};

}
