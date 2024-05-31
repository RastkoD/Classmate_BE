package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEntity extends UserAccountEntity {
	
	@Column(name = "jmbg", nullable = false)
	@NotNull(message = "Personal ID number must be provided.")
	@Pattern(regexp = "^\\d{8}$", message = "Personal ID number must be exactly 8 numbers long.")
	private String jmbg;

	/*
	private LocalDate dateOfBirth;
	private LocalDate dateOfEnrollment;
	private String address;
	private String city;
	private String country;
	*/
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "guardian")
	private GuardianEntity guardian;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "attendingClass")
	private ClassEntity attendingClass;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<AssessmentEntity> assessments;

	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public GuardianEntity getGuardian() {
		return guardian;
	}

	public void setGuardian(GuardianEntity guardian) {
		this.guardian = guardian;
	}

	public ClassEntity getAttendingClass() {
		return attendingClass;
	}

	public void setAttendingClass(ClassEntity attendingClass) {
		this.attendingClass = attendingClass;
	}

	public List<AssessmentEntity> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<AssessmentEntity> assessments) {
		this.assessments = assessments;
	}

	@Override
	public String toString() {
		return "StudentEntity [jmbg=" + jmbg + ", guardian=" + guardian + ", studentClass=" + attendingClass
				+ ", assessments=" + assessments + "]";
	}

	
}
