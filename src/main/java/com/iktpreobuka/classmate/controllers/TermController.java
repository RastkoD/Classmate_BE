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

import com.iktpreobuka.classmate.entities.SchoolYearEntity;
import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.entities.dto.TermDTO;
import com.iktpreobuka.classmate.entities.mappers.TermMapper;
import com.iktpreobuka.classmate.services.SchoolYearDao;
import com.iktpreobuka.classmate.services.TermDao;

@RestController
@RequestMapping(value = "/api/terms")
@CrossOrigin(origins = "*")
public class TermController {
	
	@Autowired
	private TermDao termDao;
	
	@Autowired
	private TermMapper termMapper;
	
	@Autowired
	private SchoolYearDao schoolYearDao;

	@GetMapping
	public ResponseEntity<List<TermDTO>> getAll() {
		
		List<TermEntity> terms = termDao.getAllTerms();
		List<TermDTO> termDTOs = terms.stream()
				.map(termMapper::toDTO)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(termDTOs, HttpStatus.OK);
	}

	// Get By Id
	@GetMapping(value = "/{termId}")
	public ResponseEntity<?> getById(@PathVariable Long termId) {

		TermEntity term = termDao.getTermById(termId).orElse(null);
		
		if(term == null) {
			return new ResponseEntity<>("Term not found.", HttpStatus.NOT_FOUND);
		}
		
		TermDTO termDTO = termMapper.toDTO(term);
		termDTO.setSchoolYearName(term.getSchoolYear().getSchoolYearName());
		
		return new ResponseEntity<>(termDTO, HttpStatus.OK);
	}

	// Create New Term
	@PostMapping(value = "/create")
	public ResponseEntity<?> createTerm(@RequestBody TermDTO newTermDTO) {
		
		SchoolYearEntity schoolYear = schoolYearDao.findSchoolYearByName(newTermDTO.getSchoolYearName());
		if(schoolYear == null) {
			return new ResponseEntity<>("School year not found", HttpStatus.NOT_FOUND);
		}
		
		TermEntity newTerm = termMapper.toEntity(newTermDTO);
		newTerm.setSchoolYear(schoolYear);
		TermEntity createdTerm = termDao.createTerm(newTerm);
		TermDTO createdTermDTO = termMapper.toDTO(createdTerm);
		createdTermDTO.setSchoolYearName(createdTerm.getSchoolYear().getSchoolYearName());
		
		return new ResponseEntity<>(createdTermDTO, HttpStatus.CREATED);
	}

	// Update Term
	@PutMapping(value = "/update/{termId}")
	public ResponseEntity<?> modifyTerm(@PathVariable Long termId,
			@RequestBody TermDTO updatedTermDTO) {
		
		TermEntity updatedTerm = termMapper.toEntity(updatedTermDTO);
		
		if(!termDao.getTermById(termId).isPresent()) {
			
			return new ResponseEntity<>("Term not found.", HttpStatus.NOT_FOUND);
		}
		
		updatedTerm.setTermId(termId);
		TermEntity modifiedTerm = termDao.updateTerm(termId, updatedTerm);
		TermDTO modifiedTermDTO = termMapper.toDTO(modifiedTerm);
		
		return new ResponseEntity<>(modifiedTermDTO, HttpStatus.OK);
	}

	// Delete Term
	@DeleteMapping(value = "/delete/{termId}")
	public ResponseEntity<?> deleteTerm(@PathVariable Long termId) {
		
		if(!termDao.getTermById(termId).isPresent()) {
			
			return new ResponseEntity<>("Term not found.", HttpStatus.NOT_FOUND);		
		}
		
		termDao.deleteTerm(termId);
		
		return new ResponseEntity<>("Term deleted successfully.", HttpStatus.OK);
	}
}
