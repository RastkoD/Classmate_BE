package com.iktpreobuka.classmate.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.dto.GuardianDTO;
import com.iktpreobuka.classmate.entities.dto.StudentDTO;
import com.iktpreobuka.classmate.entities.mappers.GuardianMapper;
import com.iktpreobuka.classmate.entities.mappers.StudentMapper;
import com.iktpreobuka.classmate.services.GuardianDao;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/guardians")
@CrossOrigin(origins = "*")
public class GuardianController {
	
	@Autowired
	private GuardianDao guardianDao;
	
	@Autowired
	GuardianMapper guardianMapper;
	
	@Autowired
	StudentMapper studentMapper;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<GuardianEntity> guardians = guardianDao.getAllGuardians();
		List<GuardianDTO> guardianDTOs = guardians.stream()
				.map(guardianMapper::toDTO)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(guardianDTOs, HttpStatus.OK);
	}

	// Get By Id
	@GetMapping(value = "/{guardianId}")
	public ResponseEntity<?> getById(@PathVariable Long guardianId) {
		GuardianEntity guardian = guardianDao.getGuardianById(guardianId);
		
		if(guardian == null) {
			return new ResponseEntity<>("Guardian not found", HttpStatus.NOT_FOUND);
		}
		GuardianDTO guardianDTO = guardianMapper.toDTO(guardian);
		
		return new ResponseEntity<>(guardianDTO, HttpStatus.OK);
	}

	// Create New Guardian
	@PostMapping(value = "/create")
	public ResponseEntity<?> createGuardian(@Valid @RequestBody GuardianDTO newGuardianDTO) {
		GuardianEntity newGuardian = guardianMapper.toEntity(newGuardianDTO);
		GuardianEntity createdGuardian = guardianDao.createGuardian(newGuardian);
		GuardianDTO createdGuardianDTO = guardianMapper.toDTO(createdGuardian);
		
		return new ResponseEntity<>(createdGuardianDTO, HttpStatus.CREATED);
	}

	// Update Guardian
	@PutMapping(value = "/update/{guardianId}")
	public ResponseEntity<?> modifyGuardian(@PathVariable Long guardianId,
			@Valid @RequestBody GuardianDTO updatedGuardianDTO) {
		GuardianEntity updatedGuardian = guardianMapper.toEntity(updatedGuardianDTO);
		GuardianEntity modifiedGuardian = guardianDao.updateGuardian(guardianId, updatedGuardian);
		
		if(modifiedGuardian == null) {
			return new ResponseEntity<>("Failed to update guardian.", HttpStatus.BAD_REQUEST);
		}
		GuardianDTO modifiedGuardianDTO = guardianMapper.toDTO(modifiedGuardian);
		
		return new ResponseEntity<>(modifiedGuardianDTO, HttpStatus.OK);
	}

	// Delete Guardian
	@DeleteMapping(value = "/delete/{guardianId}")
	public ResponseEntity<?> deleteGuardian(@PathVariable Long guardianId) {
		GuardianEntity guardian = guardianDao.getGuardianById(guardianId);
		
		if(guardian == null) {
			return new ResponseEntity<>("Guardian not found", HttpStatus.NOT_FOUND);
		}
		guardianDao.deleteGuardian(guardianId);
		
		return new ResponseEntity<>("Guardian deleted", HttpStatus.OK);
	}
	
	// Get All Wards
	@GetMapping(value = "/{guardianId}/wards")
	public ResponseEntity<?> getAllWards(@PathVariable Long guardianId) {
		GuardianEntity guardian = guardianDao.getGuardianById(guardianId);
		
		if(guardian == null) {
			return new ResponseEntity<>("Guardian not found", HttpStatus.NOT_FOUND);
		}
		List<StudentDTO> wardDTOs = guardian.getWards().stream()
				.map(studentMapper::toDTO)
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(wardDTOs, HttpStatus.OK);
	}
	
	/* /project/users/changePassword/{id} da bi vrednost atributa password mogla da bude
	zamenjena sa novom vrednošću, neophodno je da se vrednost
	stare lozinke korisnika poklapa sa vrednošću stare lozinke
	prosleđene kao RequestParam */
	
	// Exception Hanlder
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    	Map<String, String> errors = new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error) -> {
    		String fieldName = "";
    		String errorMessage = "";
    		if (error instanceof FieldError) {
    			fieldName = ((FieldError) error).getField();
    			errorMessage = error.getDefaultMessage();
    		} else if (error instanceof ObjectError) {
    			fieldName = ((ObjectError) error).getObjectName();
    			errorMessage = error.getDefaultMessage();
    		}
    		errors.put(fieldName, errorMessage);
    	});
    	return errors;
    }
}

