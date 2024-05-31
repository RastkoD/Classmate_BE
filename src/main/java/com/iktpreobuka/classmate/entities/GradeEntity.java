package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iktpreobuka.classmate.entities.enums.GradeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "grade")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "grade_sequence_generator")
	@SequenceGenerator(name = "grade_sequence_generator", sequenceName = "grade_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long gradeId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "grade")
	private GradeEnum grade;
	
	@Column(name = "grade_order")
	private Long gradeOrder; // For Sorting Purposes 
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<ClassEntity> classes;
	
	@OneToMany(mappedBy = "courseGrade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<CourseEntity> courses;

	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public GradeEnum getGrade() {
		return grade;
	}

	public void setGrade(GradeEnum grade) {
		this.grade = grade;
	}

	public Long getGradeOrder() {
		return gradeOrder;
	}

	public void setGradeOrder(Long gradeOrder) {
		this.gradeOrder = gradeOrder;
	}

	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

	public List<CourseEntity> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "GradeEntity [gradeId=" + gradeId + ", grade=" + grade + ", gradeOrder=" + gradeOrder + ", classes="
				+ classes + ", courses=" + courses + "]";
	}

	
}
