package com.iktpreobuka.classmate.services;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileHandlerImpl implements FileHandler {

	@Override
	public File getLogs() {
		String path = "C:\\Users\\ralel\\Desktop\\bootcamp\\5Backend\\classmate\\logs\\\\spring-boot-logging.log";
	
		File log = new File(path);
		
		return log;
	
	}

}
