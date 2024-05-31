package com.iktpreobuka.classmate.entities.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.classmate.entities.enums.AssessmentTypeEnum;
import com.iktpreobuka.classmate.security.Views;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AssessmentDTO {

	@JsonView(Views.AdminView.class)
    private Long assessmentId;
	
	@JsonView(Views.StudentView.class)
	@NotNull(message = "Grade must be provided.")
	@Min(value = 1, message = "Grade must be greater than or equal to {value}.")
	@Max(value = 5, message = "Grade must be less than or equal to {value}.")
    private Integer mark;
	
    @JsonView(Views.AdminView.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime date;
    
    @JsonView(Views.AdminView.class)
    @Size(min = 4, max = 100, message = "Comment must be between {min} and {max} characters long.")
    private String comment;
    
    @JsonView(Views.StudentView.class)
    @NotNull(message = "Assessment type type null or invalid. Accepted values: [ORAL, TEST, ESSAY, PARTICIPATION, HOMEWORK, OTHER, FINAL]")
    private AssessmentTypeEnum assessmentType;
    
    @JsonView(Views.AdminView.class)
    @NotNull(message = "Teacher type must not be null.")
    private Long teacherId;
    
    @JsonView(Views.StudentView.class)
    private String teacherUsername;
    
    @JsonView(Views.AdminView.class)
    @NotNull(message = "Course type must not be null.")
    private Long courseId;
    
    @JsonView(Views.StudentView.class)
    private String courseName;
    
    @JsonView(Views.AdminView.class)
    @NotNull(message = "Student type must not be null.")
    private Long studentId;
    
    @JsonView(Views.StudentView.class)
    private String studentUsername;
    
	public AssessmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssessmentDTO(Long assessmentId, Integer mark, LocalDateTime date, String comment,
			AssessmentTypeEnum assessmentType, Long teacherId, String teacherUsername, Long courseId, String courseName,
			Long studentId, String studentUsername) {
		super();
		this.assessmentId = assessmentId;
		this.mark = mark;
		this.date = date;
		this.comment = comment;
		this.assessmentType = assessmentType;
		this.teacherId = teacherId;
		this.teacherUsername = teacherUsername;
		this.courseId = courseId;
		this.courseName = courseName;
		this.studentId = studentId;
		this.studentUsername = studentUsername;
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

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherUsername() {
		return teacherUsername;
	}

	public void setTeacherUsername(String teacherUsername) {
		this.teacherUsername = teacherUsername;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	@Override
	public String toString() {
		return "AssessmentDTO [assessmentId=" + assessmentId + ", mark=" + mark + ", date=" + date + ", comment="
				+ comment + ", assessmentType=" + assessmentType + ", teacherId=" + teacherId + ", teacherUsername="
				+ teacherUsername + ", courseId=" + courseId + ", courseName=" + courseName + ", studentId=" + studentId
				+ ", studentUsername=" + studentUsername + "]";
	}

	
	
}

