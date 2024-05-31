package com.iktpreobuka.classmate.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.entities.dto.CourseDTO;
import com.iktpreobuka.classmate.entities.mappers.CourseMapper;
import com.iktpreobuka.classmate.repositories.GradeRepository;
import com.iktpreobuka.classmate.repositories.TermRepository;
import com.iktpreobuka.classmate.services.CourseDao;

@RestController
@RequestMapping(value = "/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private TermRepository termRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@GetMapping
	 public ResponseEntity<?> getAll() {
        Iterable<CourseEntity> courses = courseDao.getAllCourses();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (CourseEntity course : courses) {
            CourseDTO courseDTO = CourseMapper.toDTO(course);

            TermEntity termEntity = course.getTerm();
            if (termEntity != null) {
                courseDTO.setTermId(termEntity.getTermId());
            }
            
            GradeEntity gradeEntity = course.getCourseGrade();
            if (gradeEntity != null) {
                courseDTO.setGradeId(course.getCourseId());
            }

            courseDTOs.add(courseDTO);
        }

        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }

	// Get By Id
	@GetMapping(value = "/{courseId}")
	public ResponseEntity<?> getById(@PathVariable Long courseId) {
		CourseEntity course = courseDao.getCourseById(courseId);

        if (course == null) {
            return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
        }

        CourseDTO courseDTO = CourseMapper.toDTO(course);

        TermEntity termEntity = course.getTerm();
        if (termEntity != null) {
            courseDTO.setTermId(termEntity.getTermId());
        }
        
        GradeEntity gradeEntity = course.getCourseGrade();
        if (gradeEntity != null) {
            courseDTO.setGradeId(course.getCourseId());
        }

        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
	}

	// Create New Course
	@PostMapping(value = "/create")
	public ResponseEntity<?> createCourse(@RequestBody CourseDTO newCourseDTO) {
		try {
            CourseEntity newCourse = CourseMapper.toEntity(newCourseDTO);

            TermEntity termEntity = termRepository.findById(newCourseDTO.getTermId()).orElse(null);
            newCourse.setTerm(termEntity);
            
            GradeEntity gradeEntity = gradeRepository.findById(newCourseDTO.getGradeId()).orElse(null);
            newCourse.setCourseGrade(gradeEntity);

            CourseEntity createdCourse = courseDao.createCourse(newCourse);

            CourseDTO createdCourseDTO = CourseMapper.toDTO(createdCourse);
            createdCourseDTO.setTermId(termEntity.getTermId());
            createdCourseDTO.setGradeId(gradeEntity.getGradeId());
            
            logger.info("Admin created new course: " + createdCourseDTO.getCourseName());

            return new ResponseEntity<>(createdCourseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create course: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

	// Update Course
	@PutMapping(value = "/update/{courseId}")
	public ResponseEntity<?> modifyCourse(@PathVariable Long courseId,
			@RequestBody CourseDTO updatedCourseDTO) {
		try {
            CourseEntity existingCourse = courseDao.getCourseById(courseId);
            if (existingCourse == null) {
                return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
            }

            existingCourse.setCourseName(updatedCourseDTO.getCourseName());
            existingCourse.setWeekUnits(updatedCourseDTO.getWeekUnits());

            TermEntity termEntity = termRepository.findById(updatedCourseDTO.getTermId()).orElse(null);
            existingCourse.setTerm(termEntity);
            
            GradeEntity gradeEntity = gradeRepository.findById(updatedCourseDTO.getGradeId()).orElse(null);
            existingCourse.setCourseGrade(gradeEntity);

            CourseEntity updatedCourse = courseDao.updateCourse(courseId, existingCourse);

            CourseDTO updatedCourseResponse = CourseMapper.toDTO(updatedCourse);
            updatedCourseResponse.setTermId(termEntity.getTermId());
            updatedCourseResponse.setGradeId(gradeEntity.getGradeId());
            
            logger.info("Admin updated course: " + updatedCourse.getCourseName());

            return new ResponseEntity<>(updatedCourseResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update course: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

	// Delete Course
	@DeleteMapping(value = "/delete/{courseId}")
	public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
		CourseEntity deleteCourse = courseDao.getCourseById(courseId);

		if(deleteCourse == null) {
			return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
		}

		courseDao.deleteCourse(courseId);

		return new ResponseEntity<>("Course deleted.", HttpStatus.OK);
	}

	// Create Course With Teacher

	// Add Teacher To Course...Teacher_Course Entity

	// Get Course By courseName

	// Remove Grade From Course

	// Add Grade To Course

	// Get Courses By Grade...Maybe In Grade

	// Get Courses By Class...Maybe In Class

	// Get All CourseTeacher combo by CourseId
}
