package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.repositories.CourseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CourseDaoImpl implements CourseDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Iterable<CourseEntity> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public CourseEntity getCourseById(Long courseId) {
		return courseRepository.findById(courseId).orElse(null);
	}

	@Override
	public CourseEntity createCourse(CourseEntity newCourse) {
		return courseRepository.save(newCourse);
	}

	@Override
	public CourseEntity updateCourse(Long courseId, CourseEntity updatedCourse) {
		if(!courseRepository.existsById(courseId)) {
			return null;
		}
		
		CourseEntity existingCourse = courseRepository.findById(courseId).orElse(null);
		
		if (existingCourse != null) {
	        updatedCourse.setVersion(existingCourse.getVersion());
	    }
		
		updatedCourse.setCourseId(courseId);
		return courseRepository.save(updatedCourse);
	}

	@Override
	public void deleteCourse(Long courseId) {
		courseRepository.deleteById(courseId);
		
	}

	@Override
	public List<CourseEntity> getCoursesByTermAndGrade(TermEntity termEntity, GradeEntity gradeEntity) {
        String jpql = "SELECT c FROM CourseEntity c WHERE c.term = :termEntity AND c.courseGrade = :gradeEntity";
        return em.createQuery(jpql, CourseEntity.class)
                .setParameter("termEntity", termEntity)
                .setParameter("gradeEntity", gradeEntity)
                .getResultList();
    }
}
