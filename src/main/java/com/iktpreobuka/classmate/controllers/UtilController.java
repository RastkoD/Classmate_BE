package com.iktpreobuka.classmate.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.classmate.services.FileHandler;

@RestController
@RequestMapping(value = "/api/util/")
@CrossOrigin(origins = "*")
public class UtilController {
	
	@Autowired
	private FileHandler fileHandler;

	//@Secured("ADMIN")
	@GetMapping(value = "/download")
	public ResponseEntity<Resource> downloadLogs() {
	    try {
	        File file = fileHandler.getLogs();
	        
	        if (file.exists()) {
	            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	            
	            HttpHeaders responseHeaders = new HttpHeaders();
	            responseHeaders.add("content-disposition", "attachment; filename=" + "logs.txt");
	            
	            return ResponseEntity.ok()
	                    .headers(responseHeaders)
	                    .contentLength(file.length())
	                    .contentType(MediaType.parseMediaType("application/octet-stream"))
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
}
