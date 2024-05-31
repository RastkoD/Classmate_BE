package com.iktpreobuka.classmate.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.iktpreobuka.classmate.controllers.util.RESTError;
import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.SchoolYearEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.dto.ClassDTO;
import com.iktpreobuka.classmate.entities.mappers.ClassMapper;
import com.iktpreobuka.classmate.repositories.ClassRepository;
import com.iktpreobuka.classmate.repositories.CourseRepository;
import com.iktpreobuka.classmate.repositories.GradeRepository;
import com.iktpreobuka.classmate.repositories.SchoolYearRepository;
import com.iktpreobuka.classmate.repositories.TeacherRepository;
import com.iktpreobuka.classmate.repositories.Teacher_CourseRepository;
import com.iktpreobuka.classmate.services.ClassDao;
import com.iktpreobuka.classmate.services.StudentDao;

@RestController
@RequestMapping(value = "/api/classes")
@CrossOrigin(origins = "*")
public class ClassController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassDao classDao;

	@Autowired
	private SchoolYearRepository schoolYearRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private Teacher_CourseRepository teacherCourseRepository;
	
	@Autowired
	StudentDao studentDao;

	@GetMapping
	public ResponseEntity<?> getAllClasses() {
		List<ClassEntity> classes = classDao.getAllClasses();
		List<ClassDTO> classDTOs = new ArrayList<>();

		for(ClassEntity classEntity : classes) {
			ClassDTO classDTO = ClassMapper.toDTO(classEntity);

			SchoolYearEntity schoolYear = classEntity.getSchoolYear();
			if(schoolYear != null) {
				classDTO.setSchoolYearId(schoolYear.getSchoolYearId());
			}
			GradeEntity grade = classEntity.getGrade();
			if(grade != null) {
				classDTO = ClassMapper.toDTO(classEntity);
			}
			TeacherEntity homeroomTeacher = classEntity.getHomeroomTeacher();
			if(homeroomTeacher != null) {
				classDTO = ClassMapper.toDTO(classEntity);
			}
			
			classDTOs.add(classDTO);
		}

		return new ResponseEntity<>(classDTOs, HttpStatus.OK);
	}

	// Get By Id
	@GetMapping(value = "/{classId}")
	public ResponseEntity<?> getById(@PathVariable Long classId) {
		ClassEntity classEntity = classDao.getClassById(classId);

		if(classEntity == null) {
			return new ResponseEntity<>("Class not found", HttpStatus.NOT_FOUND);
		}

		ClassDTO classDTO = ClassMapper.toDTO(classEntity);

		SchoolYearEntity schoolYear = classEntity.getSchoolYear();
		if(schoolYear != null) {
			classDTO.setSchoolYearId(schoolYear.getSchoolYearId());
		}
		GradeEntity grade = classEntity.getGrade();
		if(grade != null) {
			classDTO = ClassMapper.toDTO(classEntity);
		}
		TeacherEntity homeroomTeacher = classEntity.getHomeroomTeacher();
		if(homeroomTeacher != null) {
			classDTO = ClassMapper.toDTO(classEntity);
		}

		return new ResponseEntity<>(classDTO, HttpStatus.OK);
	}

	// Create New Class
	@PostMapping(value = "/create")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createClass(@RequestBody ClassDTO newClassDTO) {
		try {
			ClassEntity newClassEntity = ClassMapper.toEntity(newClassDTO);

			SchoolYearEntity schoolYear = schoolYearRepository.findById(newClassDTO.getSchoolYearId()).orElse(null);
			newClassEntity.setSchoolYear(schoolYear);

			GradeEntity grade = gradeRepository.findById(newClassDTO.getGradeId()).orElse(null);
			newClassEntity.setGrade(grade);

			if (newClassDTO.getHomeroomTeacherId() != null) {
				TeacherEntity teacher = teacherRepository.findById(newClassDTO.getHomeroomTeacherId()).orElse(null);
				newClassEntity.setHomeroomTeacher(teacher);
			}

			ClassEntity createdClass = classDao.createClass(newClassEntity);

			ClassDTO createdDTO = ClassMapper.toDTO(createdClass);
			/*
			createdDTO.setSchoolYearId(schoolYear.getSchoolYearId());
			createdDTO.setGradeId(grade.getGradeId());
			createdDTO.setHomeroomTeacherId(teacher.getUserId());
			 */
			if (schoolYear != null) {
				createdDTO.setSchoolYearId(schoolYear.getSchoolYearId());
			}
			if (grade != null) {
				createdDTO.setGradeId(grade.getGradeId());
			}
			if (newClassDTO.getHomeroomTeacherId() != null) {
				createdDTO.setHomeroomTeacherId(newClassDTO.getHomeroomTeacherId());
			}
			
			logger.info("Class created by admin, in gradeId: " + createdDTO.getGradeId() + ", class name: " + createdDTO.getClassName() + ", with homeroom teacherId: " + createdDTO.getHomeroomTeacherId() + ", for yearId: " + createdDTO.getSchoolYearId());

			return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Update Class
	@PutMapping(value = "/update/{classId}")
	public ResponseEntity<?> modifyClass(@PathVariable Long classId,
			@RequestBody ClassDTO updatedClassDTO) {
		try {
			ClassEntity existingClass = classDao.getClassById(classId);
			if(existingClass == null) {
				return new ResponseEntity<>("Class not found.", HttpStatus.NOT_FOUND);
			}

			existingClass.setClassName(updatedClassDTO.getClassName());

			SchoolYearEntity schoolYear = schoolYearRepository.findById(updatedClassDTO.getSchoolYearId()).orElse(null);
			existingClass.setSchoolYear(schoolYear);

			GradeEntity grade = gradeRepository.findById(updatedClassDTO.getGradeId()).orElse(null);
			existingClass.setGrade(grade);

			TeacherEntity teacher = teacherRepository.findById(updatedClassDTO.getHomeroomTeacherId()).orElse(null);
			existingClass.setHomeroomTeacher(teacher);

			ClassEntity updatedClass = classDao.updateClass(classId, existingClass);

			ClassDTO updatedClassResponse = ClassMapper.toDTO(updatedClass);
			updatedClassResponse.setSchoolYearId(schoolYear.getSchoolYearId());
			updatedClassResponse.setGradeId(grade.getGradeId());
			updatedClassResponse.setHomeroomTeacherId(teacher.getUserId());
			
			logger.info("Class: " + existingClass.getGrade().getGradeOrder() + " " + existingClass.getClassName());

			return new ResponseEntity<>(updatedClassResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Something went wrong: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Delete Class
	@DeleteMapping(value = "/delete/{classId}")
	public ResponseEntity<?> deleteClass(@PathVariable Long classId) {
		ClassEntity deleteClass = classDao.getClassById(classId);

		if(deleteClass == null) {
			return new ResponseEntity<>("Class not found", HttpStatus.NOT_FOUND);
		}

		classDao.deleteClass(classId);

		return new ResponseEntity<>("Class deleted", HttpStatus.OK);
	}
	
	// All Students in Class
	@GetMapping(value = "/{classId}/students")
	public ResponseEntity<?> findStudentsForClass(@PathVariable Long classId) {
		if(classDao.existsById(classId)) {
			ClassEntity clazz = classDao.getClassById(classId);
			
			List<StudentEntity> students = ((List<StudentEntity>) studentDao.findByAttendingClass(clazz))
					.stream().collect(Collectors.toList());
			
			return new ResponseEntity<List<StudentEntity>>(students, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "Class not found."), HttpStatus.NOT_FOUND);
	}
	
	/*
	// Add Course/Teacher to Class
	//@Secured("ADMIN")
	@PostMapping(value = "/{classId}/courses/{courseId}/teachers/{teacherId}")
	public ResponseEntity<?> addCourseToClass(@PathVariable Long classId, @PathVariable Long courseId,
			@PathVariable Long teacherId) {
		Optional<ClassEntity> optionalClass = classRepository.findById(classId);
		Optional<CourseEntity> optionalCourse  = courseRepository.findById(courseId);
		Optional<TeacherEntity> optionalTeacher  = teacherRepository.findById(teacherId);
		
		if(optionalClass.isEmpty()) {
			return new ResponseEntity<>(new RESTError(5, "Class not found"), HttpStatus.NOT_FOUND);
		}
		
		if(optionalCourse.isEmpty()) {
			return new ResponseEntity<>(new RESTError(5, "Course not found"), HttpStatus.NOT_FOUND);
		}
		
		if(optionalTeacher.isEmpty()) {
			return new ResponseEntity<>(new RESTError(5, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		
		TeacherEntity teacher = optionalTeacher.get();
		CourseEntity course = optionalCourse.get();
		
		if(teacherCourseRepository.existsByTeacherAndCourse(teacher, course)) {
			ClassEntity clasz = classDao.addCourseForClass(classId, courseId, teacherId);
			
			if(clasz != null) {
				return new ResponseEntity<>(clasz, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(new RESTError(44, "Course alredy added"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new RESTError(11, "Teacher/Course combination not found."), HttpStatus.NOT_FOUND);
	}
	*/
	
	
	
	// Get All CourseTeacher combo for ClassId

	// Create With Homeroom Teacher

	// Add Homeroom Teacher

	// Update Homeroom Teacher

	// Get Class By Grade, Get Class By SchoolYear

	// Get All Students In This Class (value = "/class/{id}/students")

	// Get One Student In This Class

	// Get Random Student In This Class

	// Add TeacherCourse For All Students In This Class - ne, odeljenje slusa

	// Add All and/or One Course to Class maybe from Grade connection 

	// Add Class to TeacherCourse -or vice versa- Teacher_Course_Class

	// Get All CourseTeacher combo for ClassId, from Course or Teacher

	// Get All Students From Class With Subject For LoggedIn Teacher
}
