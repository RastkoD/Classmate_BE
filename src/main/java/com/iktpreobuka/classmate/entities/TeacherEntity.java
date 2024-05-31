package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.security.Views;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeacherEntity extends UserAccountEntity {
	
	@Column(name = "consultations")
	private String consultations;
	
	// private String email;
	// private String phoneNumber;
	// private String teacherImageUrl;
	// private double salary;
	
	@OneToOne(mappedBy = "homeroomTeacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonView(Views.TeacherView.class)
	private ClassEntity supervisesClass;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Teacher_Course> teacherCourse;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<AssessmentEntity> assessments;

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getConsultations() {
		return consultations;
	}

	public void setConsultations(String consultations) {
		this.consultations = consultations;
	}

	public ClassEntity getSupervisesClass() {
		return supervisesClass;
	}

	public void setSupervisesClass(ClassEntity supervisesClass) {
		this.supervisesClass = supervisesClass;
	}

	public List<Teacher_Course> getTeacherCourse() {
		return teacherCourse;
	}

	public void setTeacherCourse(List<Teacher_Course> teacherCourse) {
		this.teacherCourse = teacherCourse;
	}

	public List<AssessmentEntity> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<AssessmentEntity> assessments) {
		this.assessments = assessments;
	}

	@Override
	public String toString() {
		return "TeacherEntity [consultations=" + consultations + ", supervisesClass=" + supervisesClass
				+ ", teacherCourse=" + teacherCourse + ", assessments=" + assessments + "]";
	}

	
}
