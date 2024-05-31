package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.iktpreobuka.classmate.entities.AssessmentEntity;
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.repositories.AssessmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AssessmentDaoImpl implements AssessmentDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AssessmentRepository assessmentRepository;

	@Override
	public AssessmentEntity getAssessmentById(Long assessmentId) {
		return assessmentRepository.findById(assessmentId).orElse(null);
	}

	@Override
	@Transactional
    @Validated
	public AssessmentEntity createAssessment(@Valid AssessmentEntity assessment) {
		return assessmentRepository.save(assessment);
	}

	@Override
	@Transactional
    @Validated
	public AssessmentEntity updateAssessment(Long assessmentId, @Valid AssessmentEntity updatedAssessment) {
		if (!assessmentRepository.existsById(assessmentId)) {
			return null;
		}

		AssessmentEntity existingAssess = assessmentRepository.findById(assessmentId).orElse(null);

		if (existingAssess != null) {
			updatedAssessment.setVersion(existingAssess.getVersion());
		}

		updatedAssessment.setAssessmentId(assessmentId);
		return assessmentRepository.save(updatedAssessment);
	}

	@Override
	public void deleteAssessment(Long assessmentId) {
		assessmentRepository.deleteById(assessmentId);
	}

	@Override
	public List<AssessmentEntity> getAllAssessments() {
		return (List<AssessmentEntity>) assessmentRepository.findAll();
	}

	@Override
	public List<AssessmentEntity> getAssessmentsByStudentId(Long studentId) {
		try {
			String sql = "SELECT a FROM AssessmentEntity a WHERE a.student.id = :studentId";
			TypedQuery<AssessmentEntity> query = em.createQuery(sql, AssessmentEntity.class);
			query.setParameter("studentId", studentId);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AssessmentEntity> getAssessmentsByStudentIdAndCourseId(Long studentId, Long courseId) {
		try {
			String sql = "SELECT a FROM AssessmentEntity a WHERE a.student.id = :studentId AND a.course.id = :courseId";
			TypedQuery<AssessmentEntity> query = em.createQuery(sql, AssessmentEntity.class);
			query.setParameter("studentId", studentId);
			query.setParameter("courseId", courseId);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public GuardianEntity getStudentGuardian(AssessmentEntity assessment) {
		GuardianEntity guardian = assessment.getStudent().getGuardian();
		
		if(guardian == null) {
			return null;
		}
		
		return guardian;
	}

	@Override
	public StudentEntity getStudentFromAssess(AssessmentEntity assessment) {
		StudentEntity student = assessment.getStudent();
		
		return student;
	}

	@Override
	public CourseEntity getCourseFromAssess(AssessmentEntity assessment) {
		CourseEntity course = assessment.getCourse();
		
		return course;
	}

	@Override
	public TeacherEntity getTeacherFromAssess(AssessmentEntity assessment) {
		TeacherEntity teacher = assessment.getTeacher();
		
		return teacher;
	}

}

