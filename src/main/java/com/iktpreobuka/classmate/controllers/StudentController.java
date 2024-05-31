package com.iktpreobuka.classmate.controllers;
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

import com.iktpreobuka.classmate.controllers.util.RESTError;
import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.dto.StudentDTO;
import com.iktpreobuka.classmate.entities.mappers.StudentMapper;
import com.iktpreobuka.classmate.services.ClassDao;
import com.iktpreobuka.classmate.services.GuardianDao;
import com.iktpreobuka.classmate.services.StudentDao;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private GuardianDao guardianDao;

	@Autowired
	private ClassDao classDao;

	@GetMapping
	public ResponseEntity<List<StudentDTO>> getAll() {
		List<StudentEntity> students = studentDao.getAllStudents();
		List<StudentDTO> studentDTOs = students.stream()
				.map(studentMapper::toDTO)
				.collect(Collectors.toList());

		return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/{studentId}")
	public ResponseEntity<StudentDTO> getById(@PathVariable Long studentId) {
		StudentEntity student = studentDao.getStudentById(studentId);

		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(studentMapper.toDTO(student), HttpStatus.OK);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO newStudentDTO) {
		StudentEntity newStudent = studentMapper.toEntity(newStudentDTO);
		StudentEntity createdStudent = studentDao.createStudent(newStudent);

		return new ResponseEntity<>(studentMapper.toDTO(createdStudent), HttpStatus.CREATED);
	}

	@PutMapping(value = "/update/{studentId}")
	public ResponseEntity<StudentDTO> modifyStudent(@PathVariable Long studentId,
			@Valid @RequestBody StudentDTO updatedStudentDTO) {
		StudentEntity existingStudent = studentDao.getStudentById(studentId);
		if (existingStudent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		StudentEntity updatedStudent = studentMapper.toEntity(updatedStudentDTO);

		updatedStudent.setUserId(studentId);
		studentDao.updateStudent(studentId, updatedStudent);

		logger.info("Admin updated student: " + updatedStudent.getUsername());

		return new ResponseEntity<>(studentMapper.toDTO(updatedStudent), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long studentId) {
		StudentEntity student = studentDao.getStudentById(studentId);

		if (student == null) {
			return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
		}
		studentDao.deleteStudent(studentId);

		return new ResponseEntity<>("Student deleted", HttpStatus.OK);
	}

	@PostMapping(value = "/{studentId}/guardian/{guardianId}")
	public ResponseEntity<?> addGuardianToStudent(@PathVariable Long studentId, @PathVariable Long guardianId) {
		StudentEntity student = studentDao.getStudentById(studentId);
		GuardianEntity guardian = guardianDao.getGuardianById(guardianId);

		if (student == null || guardian == null) {
			return new ResponseEntity<>("Student or Guardian not found", HttpStatus.NOT_FOUND);
		}

		student.setGuardian(guardian);
		studentDao.updateStudent(studentId, student);

		logger.info("Guardian: " + guardian.getUsername() + " added to student: " + student.getUsername());

		return new ResponseEntity<>("Guardian added to student successfully", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{studentId}/guardian")
	public ResponseEntity<?> removeGuardianFromStudent(@PathVariable Long studentId) {
		StudentEntity student = studentDao.getStudentById(studentId);

		if (student == null) {
			return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
		}

		student.setGuardian(null);
		studentDao.updateStudent(studentId, student);

		logger.info("Guardian: " + student.getGuardian().getUsername() + " removed from student " + student.getUsername());

		return new ResponseEntity<>("Guardian removed from student successfully", HttpStatus.OK);
	}

	// Add Student To Class
	@PostMapping(value = "/{studentId}/class/{classId}")
	public ResponseEntity<?> addClass(@PathVariable Long studentId, @PathVariable Long classId) {
		if (studentDao.existsById(studentId)) {
			if (classDao.existsById(classId)) {
				StudentEntity student = studentDao.getStudentById(studentId);
				
				student.setAttendingClass(classDao.getClassById(classId));
				studentDao.save(student);
				
				return new ResponseEntity<>(studentMapper.toDTO(student), HttpStatus.OK);
			}
			
			return new ResponseEntity<RESTError>(new RESTError(1, "Class not found."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Student not found."), HttpStatus.NOT_FOUND);
	}

	// Change Class
	@PutMapping(value = "/{studentId}/class/{classId}")
	public ResponseEntity<?> updateClass(@PathVariable Long studentId, @PathVariable Long classId) {
		if (studentDao.existsById(studentId)) {
			if (classDao.existsById(classId)) {
				
				StudentEntity student = studentDao.getStudentById(studentId);
				student.setAttendingClass(classDao.getClassById(classId));
				
				studentDao.save(student);
				
				return new ResponseEntity<>(studentMapper.toDTO(student), HttpStatus.OK);
			}
			
			return new ResponseEntity<RESTError>(new RESTError(1, "Class not found."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Student not found."), HttpStatus.NOT_FOUND);
	}
	/*
    @PostMapping(value = "/create-with-guardian")
    public ResponseEntity<?> createStudentWithGuardian(@RequestBody StudentDTO newStudentDTO) {
        if (newStudentDTO.getGuardian() == null) {
            return new ResponseEntity<>("Guardian information is required", HttpStatus.BAD_REQUEST);
        }

        GuardianDTO guardianDTO = newStudentDTO.getGuardian();
        GuardianEntity guardian;

        if (guardianDTO.getUserId() != null) {
            guardian = guardianDao.getGuardianById(guardianDTO.getUserId());
        } else {
            guardian = new GuardianEntity();
            guardian.setFirstName(guardianDTO.getFirstName());
            guardian.setLastName(guardianDTO.getLastName());
            guardian.setEmail(guardianDTO.getEmail());

            guardian = guardianDao.createGuardian(guardian);
        }

        if (guardian == null) {
            return new ResponseEntity<>("Guardian not found or created", HttpStatus.NOT_FOUND);
        }

        StudentEntity newStudent = studentMapper.toEntity(newStudentDTO);
        newStudent.setGuardian(guardian);

        StudentEntity createdStudent = studentDao.createStudent(newStudent);
        return new ResponseEntity<>(studentMapper.toDTO(createdStudent), HttpStatus.CREATED);
    }



	 */
	// Get Student By firstName, lastName

	// Get Student By JMBG

	// Remove Student From Class BITNO!!

	// Change Student's Class BITNO!!

	// Get All Student's Classes sort by schoolYearName/Grade better BITNO!!

	// Get All Student's Courses + Teachers

	/* /project/users/changePassword/{id} da bi vrednost atributa password mogla da bude
	zamenjena sa novom vrednošću, neophodno je da se vrednost
	stare lozinke korisnika poklapa sa vrednošću stare lozinke
	prosleđene kao RequestParam */

	// Exception Handler
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