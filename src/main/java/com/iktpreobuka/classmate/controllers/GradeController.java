package com.iktpreobuka.classmate.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.dto.GradeDTO;
import com.iktpreobuka.classmate.entities.mappers.GradeMapper;
import com.iktpreobuka.classmate.services.GradeDao;

@RestController
@RequestMapping(value = "/api/grades")
@CrossOrigin(origins = "*")
public class GradeController {
	
	@Autowired
	private GradeDao gradeDao;
	
	@Autowired
	private GradeMapper gradeMapper;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<GradeEntity> grades = gradeDao.getAllGrades();
		List<GradeDTO> gradeDTOs = grades.stream()
				.map(gradeMapper::toDTO)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(gradeDTOs, HttpStatus.OK);
	}

	// Get By Id
	@GetMapping(value = "/{gradeId}")
	public ResponseEntity<?> getById(@PathVariable Long gradeId) {
		GradeEntity grade = gradeDao.getGradeById(gradeId).orElse(null);
		
		if(grade == null) {
			return new ResponseEntity<>("Grade not found.", HttpStatus.NOT_FOUND);
		}
		
		GradeDTO gradeDTO = gradeMapper.toDTO(grade);
		
		return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
	}

	// Create New Grade
	@PostMapping(value = "/create")
	public ResponseEntity<?> createGrade(@RequestBody GradeDTO newGradeDTO) {
		GradeEntity newGrade = gradeMapper.toEntity(newGradeDTO);
		GradeEntity createdGrade = gradeDao.createGrade(newGrade);
		GradeDTO createdGradeDTO = gradeMapper.toDTO(createdGrade);
		
		return new ResponseEntity<>(createdGradeDTO, HttpStatus.CREATED);
	}

	// Update Grade
	@PutMapping(value = "/update/{gradeId}")
	public ResponseEntity<?> modifyGrade(@PathVariable Long gradeId,
			@RequestBody GradeDTO updatedGradeDTO) {
		
		GradeEntity updatedGrade = gradeMapper.toEntity(updatedGradeDTO);
		
		if(!gradeDao.getGradeById(gradeId).isPresent()) {
			return new ResponseEntity<>("Grade not found.", HttpStatus.NOT_FOUND);
		}
		
		updatedGrade.setGradeId(gradeId);
		GradeEntity modifiedGrade = gradeDao.updateGrade(gradeId, updatedGrade);
		GradeDTO modifiedGradeDTO = gradeMapper.toDTO(modifiedGrade);
		
		return new ResponseEntity<>(modifiedGradeDTO, HttpStatus.OK);
	}

	// Delete Grade
	@DeleteMapping(value = "/delete/{gradeId}")
	public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId) {
		if(!gradeDao.getGradeById(gradeId).isPresent()) {
			return new ResponseEntity<>("Grade not found", HttpStatus.NOT_FOUND);
		}
		
		gradeDao.deleteGrade(gradeId);
		
		return new ResponseEntity<>("Grade deleted.", HttpStatus.OK);
	}

}
