package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "teacher_course")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher_Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TeacherCourse_sequence_generator")
	@SequenceGenerator(name = "TeacherCourse_sequence_generator", sequenceName = "TeacherCourse_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long teacherCourseId;
	
	// date from - to, mozda preuzeti od course term
	//private int teacherLoad; // can do some calc with Course.weekUnits
	
	@Column(name = "substitute_teacher")
	private boolean substituteTeacher;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher")
	private TeacherEntity teacher;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "course")
	private CourseEntity course;
	
	@OneToMany(mappedBy = "teacherCourse", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Teacher_Course_Class> teacherCourseClass;
	
	@Column
	private Boolean deleted;
	
	@Version
	@JsonIgnore
	private Integer version;

	public Teacher_Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTeacherCourseId() {
		return teacherCourseId;
	}

	public void setTeacherCourseId(Long teacherCourseId) {
		this.teacherCourseId = teacherCourseId;
	}

	public boolean isSubstituteTeacher() {
		return substituteTeacher;
	}

	public void setSubstituteTeacher(boolean substituteTeacher) {
		this.substituteTeacher = substituteTeacher;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public CourseEntity getCourse() {
		return course;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	public List<Teacher_Course_Class> getTeacherCourseClass() {
		return teacherCourseClass;
	}

	public void setTeacherCourseClass(List<Teacher_Course_Class> teacherCourseClass) {
		this.teacherCourseClass = teacherCourseClass;
	}
	
	

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Teacher_Course [teacherCourseId=" + teacherCourseId + ", substituteTeacher=" + substituteTeacher
				+ ", teacher=" + teacher + ", course=" + course + ", teacherCourseClass=" + teacherCourseClass
				+ ", version=" + version + "]";
	}

	
}
