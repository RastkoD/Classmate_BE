package com.iktpreobuka.classmate.entities.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SchoolYearDTO {
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	private String schoolYearName;

	public SchoolYearDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchoolYearDTO(LocalDate startDate, LocalDate endDate, String schoolYearName) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.schoolYearName = schoolYearName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getSchoolYearName() {
		return schoolYearName;
	}

	public void setSchoolYearName(String schoolYearName) {
		this.schoolYearName = schoolYearName;
	}

}
