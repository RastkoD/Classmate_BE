package com.iktpreobuka.classmate.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.security.Views;

public class RESTError {
	
	@JsonView(Views.PrivateView.class)
	private int code;
	
	@JsonView(Views.PrivateView.class)
	private String message;

	public RESTError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
