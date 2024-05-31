package com.iktpreobuka.classmate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "teacher_course_class")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher_Course_Class {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "teacherCourseClass_sequence_generator")
	@SequenceGenerator(name = "teacherCourseClass_sequence_generator", sequenceName = "teacherCourseClass_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long teacherCourseClassId;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_course")
	private Teacher_Course teacherCourse;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "clasz")
	private ClassEntity assignedClass;
	
	@Column
	private Boolean deleted;
	
	@Version
	@JsonIgnore
	private Integer version;

	public Teacher_Course_Class() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTeacherCourseClassId() {
		return teacherCourseClassId;
	}

	public void setTeacherCourseClassId(Long teacherCourseClassId) {
		this.teacherCourseClassId = teacherCourseClassId;
	}

	public Teacher_Course getTeacherCourse() {
		return teacherCourse;
	}

	public void setTeacherCourse(Teacher_Course teacherCourse) {
		this.teacherCourse = teacherCourse;
	}

	public ClassEntity getClassEntity() {
		return assignedClass;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.assignedClass = classEntity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Teacher_Course_Class [teacherCourseClassId=" + teacherCourseClassId + ", teacherCourse=" + teacherCourse
				+ ", classEntity=" + assignedClass + ", version=" + version + "]";
	}

	
}
