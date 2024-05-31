package com.iktpreobuka.classmate.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.entities.enums.AssessmentTypeEnum;
import com.iktpreobuka.classmate.security.Views;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assessment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonRootName(value = "assessment")
public class AssessmentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "assess_sequence_generator")
	@SequenceGenerator(name = "assess_sequence_generator", sequenceName = "assess_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	@JsonView(Views.AdminView.class)
	private Long assessmentId;
	
	@Column(name = "mark", nullable = false)
	@JsonView(Views.StudentView.class)
	@NotNull(message = "Grade must be provided.")
	@Min(value = 1, message = "Grade must be greater than {value}.")
	@Max(value = 5, message = "Grade must be lesser than than {value}.")
	private Integer mark;
	
	@CreationTimestamp
	@Column(name = "assessment_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@JsonView(Views.StudentView.class)
	private LocalDateTime date; //dodaj recimo 12 sati za slanje emaila
	
	@Column(name = "comment")
	@JsonView(Views.GuardianView.class)
	@Size(min = 4, max = 100, message = "Comment must be between {min} and {max} characters long.")
	private String comment;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "assessment_type")
	@JsonView(Views.StudentView.class)
	@NotNull(message = "Assessment type type null or invalid. Accepted values: [ORAL, TEST, ESSAY, PARTICIPATION, HOMEWORK, OTHER, FINAL]")
	private AssessmentTypeEnum assessmentType;
	
	//private MarkTypeEnum markType;
	//private MarkEnum mark;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher", nullable = false)
	@NotNull(message = "Teacher type must not be null.")
	private TeacherEntity teacher;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "course", nullable = false)
	@NotNull(message = "Course type must not be null.")
	private CourseEntity course; //odavde markType ako zatreba
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student", nullable = false)
	@NotNull(message = "Student type must not be null.")
	private StudentEntity student;
	
	@Version
	@JsonIgnore
	private Integer version;

	public AssessmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public AssessmentTypeEnum getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(AssessmentTypeEnum assessmentType) {
		this.assessmentType = assessmentType;
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

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "AssessmentEntity [assessmentId=" + assessmentId + ", mark=" + mark + ", date=" + date + ", comment="
				+ comment + ", assessmentType=" + assessmentType + ", teacher=" + teacher + ", course=" + course
				+ ", student=" + student + ", version=" + version + "]";
	}

	
}
