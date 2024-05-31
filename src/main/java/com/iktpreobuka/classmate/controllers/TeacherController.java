package com.iktpreobuka.classmate.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.Teacher_Course;
import com.iktpreobuka.classmate.entities.dto.TeacherDTO;
import com.iktpreobuka.classmate.entities.mappers.TeacherMapper;
import com.iktpreobuka.classmate.repositories.CourseRepository;
import com.iktpreobuka.classmate.repositories.TeacherRepository;
import com.iktpreobuka.classmate.repositories.Teacher_CourseRepository;
import com.iktpreobuka.classmate.services.TeacherDao;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private TeacherDao teacherDao;

    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private Teacher_CourseRepository teacherCourseRepository;

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll() {
        List<TeacherEntity> teachers = teacherDao.getAllTeachers();
        List<TeacherDTO> teacherDTOs = teachers.stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(teacherDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{teacherId}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable Long teacherId) {
        TeacherEntity teacher = teacherDao.getTeacherById(teacherId);
        
        if (teacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(teacherMapper.toDTO(teacher), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO newTeacherDTO) {
        TeacherEntity newTeacher = teacherMapper.toEntity(newTeacherDTO);
        TeacherEntity createdTeacher = teacherDao.createTeacher(newTeacher);
        
        logger.info("Admin created new teacher: " + createdTeacher.getUsername());
        
        return new ResponseEntity<>(teacherMapper.toDTO(createdTeacher), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{teacherId}")
    public ResponseEntity<TeacherDTO> modifyTeacher(@PathVariable Long teacherId,
    		@Valid @RequestBody TeacherDTO updatedTeacherDTO) {
        
    	TeacherEntity existingTeacher = teacherDao.getTeacherById(teacherId);
        
        if (existingTeacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        TeacherEntity updatedTeacher = teacherMapper.toEntity(updatedTeacherDTO);
        updatedTeacher.setUserId(teacherId);
        teacherDao.updateTeacher(teacherId, updatedTeacher);
        
        logger.info("Admin updated teacher: " + updatedTeacher.getUsername());
        
        return new ResponseEntity<>(teacherMapper.toDTO(updatedTeacher), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long teacherId) {
        TeacherEntity teacher = teacherDao.getTeacherById(teacherId);
        
        if (teacher == null) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
        
        teacherDao.deleteTeacher(teacherId);
        
        return new ResponseEntity<>("Teacher deleted", HttpStatus.OK);
    }
    
    // Add Course to Teacher
    //@Secured("ADMIN")
    @PostMapping(value = "/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> addCourseForTeacher(@PathVariable Long teacherId, @PathVariable Long courseId) {
        Optional<TeacherEntity> optionalTeacher = teacherRepository.findById(teacherId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalTeacher.isEmpty()) {
            return new ResponseEntity<>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
        }

        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "Course not found."), HttpStatus.NOT_FOUND);
        }

        TeacherEntity teacher = optionalTeacher.get();
        CourseEntity course = optionalCourse.get();

        if (!teacherCourseRepository.existsByTeacherAndCourse(teacher, course) || 
            (teacherCourseRepository.findByTeacherAndCourse(teacher, course).getDeleted())) {
            
            Teacher_Course teacherCourse = new Teacher_Course();
            teacherCourse.setDeleted(false);
            teacherCourse.setTeacher(teacher);
            teacherCourse.setCourse(course);
            teacherCourseRepository.save(teacherCourse);

            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(new RESTError(11, "Teacher course combination not found."), HttpStatus.NOT_FOUND);
    }

	// Remove Course from Teacher
    //@Secured("ADMIN")
    @DeleteMapping(value = "/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> deleteCourseForTeacher(@PathVariable Long teacherId, @PathVariable Long courseId) {
        Optional<TeacherEntity> optionalTeacher = teacherRepository.findById(teacherId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalTeacher.isEmpty()) {
            return new ResponseEntity<>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
        }

        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "Course not found."), HttpStatus.NOT_FOUND);
        }

        TeacherEntity teacher = optionalTeacher.get();
        CourseEntity course = optionalCourse.get();

        if (teacherCourseRepository.existsByTeacherAndCourse(teacher, course)) {
            Teacher_Course teacherCourse = teacherCourseRepository.findByTeacherAndCourse(teacher, course);
            
            teacherCourse.setDeleted(true);
            teacherCourseRepository.save(teacherCourse);
            
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(new RESTError(7, "Teacher doesn't teach this course."), HttpStatus.NOT_FOUND);
    }
    
    //	Show All Teacher's Courses
	//@Secured("ROLE_ADMIN")
	@GetMapping(value = "/{teacherId}/courses")
	public ResponseEntity<?> getCoursesForTeacher(@PathVariable Long teacherId) {
	    Optional<TeacherEntity> optionalTeacher = teacherRepository.findById(teacherId);

	    if (optionalTeacher.isEmpty()) {
	        return new ResponseEntity<>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
	    }

	    TeacherEntity teacher = optionalTeacher.get();

	    List<CourseEntity> courses = ((List<Teacher_Course>) teacherCourseRepository.findByTeacher(teacher))
				.stream()
					.map(course -> course.getCourse())
					.collect(Collectors.toList());

	    return new ResponseEntity<>(courses, HttpStatus.OK);
	}
    
    
	// Get All CourseTeacher combo by TeacherId or Get All Courses For TeacherID BITNO!!
	
	// Get One Course for TeacherId BITNO!!
	
	// Add Course For Teacher...Teacher_Course BITNO!!
	
	// Remove Course For Teacher...Teacher_Course BITNO!!
	
	// Get All Classes For TeacherId, Get One Class, Get Students In That Class BITNO!!
	
	// Get Class Where TeacherId Is Homeroom Teacher
	
	/* /project/users/changePassword/{id} da bi vrednost atributa password mogla da bude
	zamenjena sa novom vrednošću, neophodno je da se vrednost
	stare lozinke korisnika poklapa sa vrednošću stare lozinke
	prosleđene kao RequestParam */
    
    // Exception handler
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
