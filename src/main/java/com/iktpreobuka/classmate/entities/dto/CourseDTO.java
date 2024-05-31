package com.iktpreobuka.classmate.entities.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CourseDTO {
	
	@NotNull(message = "Course name must not be null.")
	@Size(min = 5, max = 30, message = "Course name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z0-9\\s,]*$", message = "Invalid course name.")
	private String courseName;
	
	@NotNull(message = "Weekly hours must not be null.")
	@Min(value = 0, message = "Weekly hours cannot be less than zero.")
	@Max(value = 40, message = "Weekly hours cannot be above 40.")
	private int weekUnits;
	private Long termId;
	private Long gradeId;
	
	public CourseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseDTO(String courseName, int weekUnits, Long termId, Long gradeId) {
		super();
		this.courseName = courseName;
		this.weekUnits = weekUnits;
		this.termId = termId;
		this.gradeId = gradeId;
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

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	@Override
	public String toString() {
		return "CourseDTO [courseName=" + courseName + ", weekUnits=" + weekUnits + ", termId=" + termId + ", gradeId="
				+ gradeId + "]";
	}

	
}
