package com.iktpreobuka.classmate.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminEntity extends UserAccountEntity {

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
