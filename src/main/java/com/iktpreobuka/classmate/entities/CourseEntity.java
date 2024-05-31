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
@Table(name = "course")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CourseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "course_sequence_generator")
	@SequenceGenerator(name = "course_sequence_generator", sequenceName = "course_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long courseId;
	
	@Column(name = "course_name")
	private String courseName;
	
	@Column(name = "week_units")
	private int weekUnits;
	
	//private CourseNameEntity courseName;
	//private MarkTypeEnum markType;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Teacher_Course> teacherCourse;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "term")
	private TermEntity term;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "course_grade")
	private GradeEntity courseGrade;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<AssessmentEntity> assessments;
	
	@Version
	@JsonIgnore
	private Integer version;

	public CourseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getWeekUnits() {
		return weekUnits;
	}

	public void setWeekUnits(int weekUnits) {
		this.weekUnits = weekUnits;
	}

	public List<Teacher_Course> getTeacherCourse() {
		return teacherCourse;
	}

	public void setTeacherCourse(List<Teacher_Course> teacherCourse) {
		this.teacherCourse = teacherCourse;
	}

	public TermEntity getTerm() {
		return term;
	}

	public void setTerm(TermEntity term) {
		this.term = term;
	}

	public GradeEntity getCourseGrade() {
		return courseGrade;
	}

	public void setCourseGrade(GradeEntity courseGrade) {
		this.courseGrade = courseGrade;
	}

	public List<AssessmentEntity> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<AssessmentEntity> assessments) {
		this.assessments = assessments;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "CourseEntity [courseId=" + courseId + ", courseName=" + courseName + ", weekUnits=" + weekUnits
				+ ", teacherCourse=" + teacherCourse + ", term=" + term + ", courseGrade=" + courseGrade
				+ ", assessments=" + assessments + ", version=" + version + "]";
	}

	
}
