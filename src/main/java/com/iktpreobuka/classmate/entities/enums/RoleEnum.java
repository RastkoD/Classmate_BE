package com.iktpreobuka.classmate.entities.enums;

public enum RoleEnum {
	ADMIN, TEACHER, STUDENT, GUARDIAN;
	
	 public String getRoleName() {
	        return this.name();
	    }
}
