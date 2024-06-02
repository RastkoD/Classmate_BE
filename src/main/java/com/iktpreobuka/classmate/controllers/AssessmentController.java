package com.iktpreobuka.classmate.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.controllers.util.RESTError;
import com.iktpreobuka.classmate.entities.AssessmentEntity;
import com.iktpreobuka.classmate.entities.dto.AssessmentDTO;
import com.iktpreobuka.classmate.entities.mappers.AssessmentMapper;
import com.iktpreobuka.classmate.security.Views;
import com.iktpreobuka.classmate.services.AssessmentDao;
import com.iktpreobuka.classmate.services.CourseDao;
import com.iktpreobuka.classmate.services.EmailService;
import com.iktpreobuka.classmate.services.StudentDao;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/assessments")
@CrossOrigin(origins = "*")
public class AssessmentController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AssessmentDao assessmentDao;
	
	@Autowired
    private StudentDao studentDao;
	
	@Autowired
    private CourseDao courseDao;
	
	@Autowired
    private EmailService emailService;

    @Autowired
    private AssessmentMapper assessmentMapper;

    // Get All Assessments
    //@Secured("ADMIN")
    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
    	long logStartTime = System.currentTimeMillis();
    	
        List<AssessmentEntity> assessments = assessmentDao.getAllAssessments();
        List<AssessmentDTO> assessmentDTOs = assessments.stream()
                .map(assessment -> {
                    AssessmentDTO assessmentDTO = assessmentMapper.toDTO(assessment);
                    assessmentDTO.setDate(assessment.getDate());
                    return assessmentDTO;
                })
                .collect(Collectors.toList());
        
        long logEndTime = System.currentTimeMillis() - logStartTime;
        
        logger.info("Get All endpoint execution time: {} milliseconds", logEndTime);
        
        return new ResponseEntity<>(assessmentDTOs, HttpStatus.OK);
    }

    // Get Assessment By Id
    //@Secured("ADMIN")
    @GetMapping(value = "/{assessId}")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> getAssessmentById(@PathVariable Long assessId) {
    	try {
        AssessmentEntity assessment = assessmentDao.getAssessmentById(assessId);
        
        if (assessment == null) {
        	return new ResponseEntity<RESTError>(new RESTError(1, "Assessment not found"),
        			HttpStatus.NOT_FOUND);
        }
        
        AssessmentDTO assessmentDTO = assessmentMapper.toDTO(assessment);
        assessmentDTO.setDate(assessment.getDate());
        
        return new ResponseEntity<>(assessmentDTO, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred:" + e.getMessage()),
    				HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    // Create New Assessment
    //@Secured({"ADMIN", "TEACHER"})
    @PostMapping(value = "/create")
    public ResponseEntity<AssessmentDTO> createAssessment(@Valid @RequestBody AssessmentDTO newAssessmentDTO) {
        AssessmentEntity newAssessment = assessmentMapper.toEntity(newAssessmentDTO);
        AssessmentEntity createdAssessment = assessmentDao.createAssessment(newAssessment);
        AssessmentDTO createdAssessmentDTO = assessmentMapper.toDTO(createdAssessment);
        
        createdAssessmentDTO.setDate(createdAssessment.getDate());
    
        logger.info("Teacher: " + createdAssessmentDTO.getTeacherUsername() + "added mark: " + createdAssessmentDTO.getMark() + "to student: "
				+ createdAssessmentDTO.getStudentUsername() + "in subject: " + createdAssessmentDTO.getCourseName());
       
        emailService.sendAssessmentEmail(createdAssessment);// mozda staviti delay neki
        
        return new ResponseEntity<>(createdAssessmentDTO, HttpStatus.CREATED);
    }

    // Update Assessment
    //@Secured("ADMIN")
    @PutMapping(value = "/update/{assessId}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable Long assessId,
    		@Valid @RequestBody AssessmentDTO updatedAssessmentDTO) {
        AssessmentEntity existingAssessment = assessmentDao.getAssessmentById(assessId);
        
        if (existingAssessment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        AssessmentEntity updatedAssessment = assessmentMapper.toEntity(updatedAssessmentDTO);
        
        updatedAssessment.setAssessmentId(assessId);
        updatedAssessment.setDate(LocalDateTime.now());
        
        AssessmentEntity modifiedAssessment = assessmentDao.updateAssessment(assessId, updatedAssessment);
        AssessmentDTO modifiedAssessmentDTO = assessmentMapper.toDTO(modifiedAssessment);
        
        modifiedAssessmentDTO.setDate(modifiedAssessment.getDate());
        
        return new ResponseEntity<>(modifiedAssessmentDTO, HttpStatus.OK);
    }

    // Delete Assessment
    //@Secured("ADMIN")
    @DeleteMapping(value = "/delete/{assessId}")
    public ResponseEntity<?> deleteAssessment(@PathVariable Long assessId) {
        AssessmentEntity assessment = assessmentDao.getAssessmentById(assessId);
        
        if (assessment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        assessmentDao.deleteAssessment(assessId);
        
        return new ResponseEntity<>("{\"message\": \"Assessment deleted.\"}", HttpStatus.OK);
    }
    
    // Get All Assessments for Student
    //@Secured({"ADMIN", "STUDENT", "PARENT"})
    @GetMapping(value = "/student/{studentId}")
    public ResponseEntity<?> getAssessmentsByStudent(@PathVariable Long studentId) {
    	List<AssessmentEntity> assessments = assessmentDao.getAssessmentsByStudentId(studentId);
    	
    	if(assessments.isEmpty()) {
    		return new ResponseEntity<>("{\"message\": \"No assessments found for this student\"}", HttpStatus.NOT_FOUND);
    	}
    	
    	List<AssessmentDTO> assessmentDTOs = assessments.stream()
    			.map(assessmentMapper::toDTO)
    			.collect(Collectors.toList());
    	
    	logger.info("{\"message\": \"All assessments returned for student: \"}" + studentDao.getStudentById(studentId).getUsername());
    	
    	return new ResponseEntity<>(assessmentDTOs, HttpStatus.OK);
    }
    
 // Get All Assessments for Student by Admin
    //@Secured("ADMIN")
    @GetMapping(value = "/student/{studentId}/admin")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> getAssessmentsByStudentForAdmin(@PathVariable Long studentId) {
    	List<AssessmentEntity> assessments = assessmentDao.getAssessmentsByStudentId(studentId);
    	
    	if(assessments.isEmpty()) {
    		return new ResponseEntity<>("{\"message\": \"No assessments found for this student\"}", HttpStatus.NOT_FOUND);
    	}
    	
    	List<AssessmentDTO> assessmentDTOs = assessments.stream()
    			.map(assessmentMapper::toDTO)
    			.collect(Collectors.toList());
    	
    	logger.info("{\"message\": \"All assessments returned for student: \"}" + studentDao.getStudentById(studentId).getUsername() + " by admin");
    	
    	return new ResponseEntity<>(assessmentDTOs, HttpStatus.OK);
    }
    
 // Get All Assessments for Student by Student
    //@Secured("STUDENT")
    @GetMapping(value = "/student/{studentId}/student")
    @JsonView(Views.StudentView.class)
    public ResponseEntity<?> getAssessmentsByStudentForStudent(@PathVariable Long studentId) {
    	List<AssessmentEntity> assessments = assessmentDao.getAssessmentsByStudentId(studentId);
    	
    	if(assessments.isEmpty()) {
    		return new ResponseEntity<>("{\"message\": \"No assessments found for this student\"}", HttpStatus.NOT_FOUND);
    	}
    	
    	List<AssessmentDTO> assessmentDTOs = assessments.stream()
    			.map(assessmentMapper::toDTO)
    			.collect(Collectors.toList());
    	
    	logger.info("{\"message\": \"All assessments returned for student: \"}" + studentDao.getStudentById(studentId).getUsername() + " by student");
    	
    	return new ResponseEntity<>(assessmentDTOs, HttpStatus.OK);
    }
    
    // Get All For StudentId and CourseId
    //@Secured({"ADMIN", "TEACHER", "PARENT"})
    @GetMapping(value = "/student/{studentId}/course/{courseId}")
    public ResponseEntity<?> getAssessmentsByStudentIdAndCourseId(@PathVariable Long studentId,
    		@PathVariable Long courseId) {
    		List<AssessmentEntity> assessments = assessmentDao.getAssessmentsByStudentIdAndCourseId(studentId, courseId);
    		
    		if(assessments.isEmpty()) {
    			return new ResponseEntity<>("{\"message\": \"No assessments found for this student and course\"}", HttpStatus.NOT_FOUND);
    		}
    		
    		List<AssessmentDTO> assessmentDTOs = assessments.stream()
    				.map(assessmentMapper::toDTO)
    				.collect(Collectors.toList());
    		
    		logger.info("{\"message\": \"All assessments returned for student: \"}" + studentDao.getStudentById(studentId).getUsername() + ", course: " + courseDao.getCourseById(courseId).getCourseName());
    		
    		return new ResponseEntity<>(assessmentDTOs, HttpStatus.OK);
    }
	
	// Exception Handler, da li ide tu?
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