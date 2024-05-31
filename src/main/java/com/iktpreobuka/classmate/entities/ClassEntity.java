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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "class")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "classEntity_sequence_generator")
	@SequenceGenerator(name = "classEntity_sequence_generator", sequenceName = "classEntity_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long classId;
	
	@Column(name = "class_name")
	private String className; //a ako je negde a, b, c a ne 1, 2, 3?
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "school_year")
	private SchoolYearEntity schoolYear;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	private GradeEntity grade;
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "homeroom_teacher")
	private TeacherEntity homeroomTeacher;
	
	@OneToMany(mappedBy = "attendingClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> students;
	
	@OneToMany(mappedBy = "assignedClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Teacher_Course_Class> teacherCourseClass;
	
	@Version
	@JsonIgnore
	private Integer version;

	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	public GradeEntity getGrade() {
		return grade;
	}

	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}

	public TeacherEntity getHomeroomTeacher() {
		return homeroomTeacher;
	}

	public void setHomeroomTeacher(TeacherEntity homeroomTeacher) {
		this.homeroomTeacher = homeroomTeacher;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public List<Teacher_Course_Class> getTeacherCourseClass() {
		return teacherCourseClass;
	}

	public void setTeacherCourseClass(List<Teacher_Course_Class> teacherCourseClass) {
		this.teacherCourseClass = teacherCourseClass;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ClassEntity [classId=" + classId + ", className=" + className + ", schoolYear=" + schoolYear
				+ ", grade=" + grade + ", homeroomTeacher=" + homeroomTeacher + ", students=" + students
				+ ", teacherCourseClass=" + teacherCourseClass + ", version=" + version + "]";
	}

	
}
