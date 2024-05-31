package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.Teacher_Course;
import com.iktpreobuka.classmate.entities.Teacher_Course_Class;
import com.iktpreobuka.classmate.repositories.ClassRepository;
import com.iktpreobuka.classmate.repositories.CourseRepository;
import com.iktpreobuka.classmate.repositories.TeacherRepository;
import com.iktpreobuka.classmate.repositories.Teacher_CourseRepository;
import com.iktpreobuka.classmate.repositories.Teacher_Course_ClassRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ClassDaoImpl implements ClassDao {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	Teacher_CourseRepository teacherCourseRepository;

	@Autowired
	Teacher_Course_ClassRepository teacherCourseClassRepository;
	
	@Override
	public List<ClassEntity> getAllClasses() {
		return (List<ClassEntity>) classRepository.findAll();
	}

	@Override
	public ClassEntity getClassById(Long classId) {
		return classRepository.findById(classId).orElse(null);
	}

	@Override
	public ClassEntity createClass(ClassEntity newClass) {
		return classRepository.save(newClass);
	}

	@Override
	public ClassEntity updateClass(Long classId, ClassEntity updatedClass) {
		if(!classRepository.existsById(classId)) {
			return null;
		}
		
		ClassEntity existingClass = classRepository.findById(classId).orElse(null);
		
		if (existingClass != null) {
			updatedClass.setVersion(existingClass.getVersion());
	    }
		
		updatedClass.setClassId(classId);
		return classRepository.save(updatedClass);
	}

	@Override
	public void deleteClass(Long classId) {
		classRepository.deleteById(classId);
		
	}

	@Override
	public boolean existsById(Long classId) {
		return classRepository.existsById(classId);
	}

	/*
	@Override
	public ClassEntity addCourseForClass(Long classId, Long courseId, Long teacherId) {
		ClassEntity clasz = classRepository.findById(classId).get();
		CourseEntity course = courseRepository.findById(courseId).get();
		TeacherEntity teacher = teacherRepository.findById(teacherId).get();
		
		Teacher_Course teacherCourse = teacherCourseRepository.findByTeacherAndCourse(teacher, course);
	
		if(!teacherCourseClassRepository.existsByClassAndTeacherCourse(clasz, teacherCourse)) {
			Teacher_Course_Class tcc = new Teacher_Course_Class();
			tcc.setClassEntity(clasz);
			tcc.setTeacherCourse(teacherCourse);
			
			teacherCourseClassRepository.save(tcc);
			
			return clasz;
		} else if(teacherCourseClassRepository.existsByClassAndTeacherCourse(clasz, teacherCourse)) {
			
			Teacher_Course_Class tcc = teacherCourseClassRepository.findByStudentAndTeacherCourse(clasz, teacherCourse);
		
			teacherCourseClassRepository.save(tcc);
			
			return clasz;
		
		}
		
		return null;
	}	*/
}
