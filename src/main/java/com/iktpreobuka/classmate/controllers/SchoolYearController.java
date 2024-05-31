package com.iktpreobuka.classmate.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.iktpreobuka.classmate.entities.SchoolYearEntity;
import com.iktpreobuka.classmate.entities.dto.SchoolYearDTO;
import com.iktpreobuka.classmate.entities.mappers.SchoolYearMapper;
import com.iktpreobuka.classmate.services.SchoolYearDao;

@RestController
@RequestMapping(value = "/api/schoolYears")
@CrossOrigin(origins = "*")
public class SchoolYearController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SchoolYearDao schoolYearDao;
	
	@Autowired
	private SchoolYearMapper schoolYearMapper;

	@GetMapping
	public ResponseEntity<List<SchoolYearDTO>> getAll() {
		
		List<SchoolYearEntity> schoolYears = schoolYearDao.getAllSchoolYears();
		List<SchoolYearDTO> schoolYearDTOs = schoolYears.stream()
				.map(schoolYearMapper::toDTO)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(schoolYearDTOs, HttpStatus.OK);
	}

	// Get By Id
	@GetMapping(value = "/{schoolYearId}")
	public ResponseEntity<?> getById(@PathVariable Long schoolYearId) {
		
		SchoolYearEntity schoolYear = schoolYearDao.getSchoolYearById(schoolYearId).orElse(null);
		
		if(schoolYear == null) {
			return new ResponseEntity<>("School Year not found.", HttpStatus.NOT_FOUND);
		}
		
		SchoolYearDTO schoolYearDTO = schoolYearMapper.toDTO(schoolYear);
		
		return new ResponseEntity<>(schoolYearDTO, HttpStatus.OK);
	}

	// Create New SchoolYear
	@PostMapping(value = "/create")
	public ResponseEntity<?> createSchoolYear(@RequestBody SchoolYearDTO newSchoolYearDTO) {
		
		SchoolYearEntity newSchoolYear = schoolYearMapper.toEntity(newSchoolYearDTO);
		SchoolYearEntity createdSchoolYear = schoolYearDao.createSchoolYear(newSchoolYear);
		SchoolYearDTO createdSchoolYearDTO = schoolYearMapper.toDTO(createdSchoolYear);
		
		logger.info("Admin created new school year " + newSchoolYear.getSchoolYearName());
		
		return new ResponseEntity<>(createdSchoolYearDTO, HttpStatus.CREATED);
	}

	// Update SchoolYear
	@PutMapping(value = "/update/{schoolYearId}")
	public ResponseEntity<?> modifySchoolYear(@PathVariable Long schoolYearId,
			@RequestBody SchoolYearDTO updatedSchoolYearDTO) {
		
		SchoolYearEntity updatedSchoolYear = schoolYearMapper.toEntity(updatedSchoolYearDTO);
		
		if(!schoolYearDao.getSchoolYearById(schoolYearId).isPresent()) {
			
			return new ResponseEntity<>("School Year not found.", HttpStatus.NOT_FOUND);
		}
		
		updatedSchoolYear.setSchoolYearId(schoolYearId);
		SchoolYearEntity modifiedSchoolYear = schoolYearDao.updateSchoolYear(schoolYearId, updatedSchoolYear);
		SchoolYearDTO modifiedSchoolYearDTO = schoolYearMapper.toDTO(modifiedSchoolYear);
		
		return new ResponseEntity<>(modifiedSchoolYearDTO, HttpStatus.OK);
	}

	// Delete SchoolYear
	@DeleteMapping(value = "/delete/{schoolYearId}")
	public ResponseEntity<?> deleteSchoolYear(@PathVariable Long schoolYearId) {
		
		if(!schoolYearDao.getSchoolYearById(schoolYearId).isPresent()) {
			
			return new ResponseEntity<>("School Year not found", HttpStatus.NOT_FOUND);
		}
		
		schoolYearDao.deleteSchoolYear(schoolYearId);
		
		return new ResponseEntity<>("School Year deleted successfully.", HttpStatus.OK);
	}
}
