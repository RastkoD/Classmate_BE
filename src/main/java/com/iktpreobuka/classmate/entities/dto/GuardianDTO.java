package com.iktpreobuka.classmate.entities.dto;

import com.iktpreobuka.classmate.utils.UniqueEmail;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class GuardianDTO extends UserAccountDTO {
	
	@UniqueEmail
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;

	public GuardianDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GuardianDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
