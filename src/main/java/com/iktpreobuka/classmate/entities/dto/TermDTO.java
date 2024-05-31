package com.iktpreobuka.classmate.entities.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iktpreobuka.classmate.entities.enums.TermNameEnum;

public class TermDTO {
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	private TermNameEnum termName;
	private String schoolYearName;
	
	public TermDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TermDTO(LocalDate startDate, LocalDate endDate, TermNameEnum termName, String schoolYearName) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.termName = termName;
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

	public TermNameEnum getTermName() {
		return termName;
	}

	public void setTermName(TermNameEnum termName) {
		this.termName = termName;
	}

	public String getSchoolYearName() {
		return schoolYearName;
	}

	public void setSchoolYearName(String schoolYearName) {
		this.schoolYearName = schoolYearName;
	}

	@Override
	public String toString() {
		return "TermDTO [startDate=" + startDate + ", endDate=" + endDate + ", termName=" + termName
				+ ", schoolYearName=" + schoolYearName + "]";
	}

}
