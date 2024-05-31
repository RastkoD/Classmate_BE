package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.AssessmentEntity;
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;

public interface AssessmentDao {
	List<AssessmentEntity> getAllAssessments();
	
    AssessmentEntity getAssessmentById(Long assessmentId);
    
    AssessmentEntity createAssessment(AssessmentEntity assessment);
    
    AssessmentEntity updateAssessment(Long assessmentId, AssessmentEntity assessment);
    
    void deleteAssessment(Long assessmentId);
    
    StudentEntity getStudentFromAssess(AssessmentEntity assessment);
    
    GuardianEntity getStudentGuardian(AssessmentEntity assessment);
    
    CourseEntity getCourseFromAssess(AssessmentEntity assessment);

	TeacherEntity getTeacherFromAssess(AssessmentEntity assessment);

	List<AssessmentEntity> getAssessmentsByStudentId(Long studentId);

	List<AssessmentEntity> getAssessmentsByStudentIdAndCourseId(Long studentId, Long courseId);
}
