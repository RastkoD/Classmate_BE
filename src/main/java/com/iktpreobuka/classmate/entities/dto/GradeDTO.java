package com.iktpreobuka.classmate.entities.dto;

import com.iktpreobuka.classmate.entities.enums.GradeEnum;

public class GradeDTO {
	
	private Long gradeId;
	private GradeEnum grade;
	private Long gradeOrder;
	
	public GradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeDTO(Long gradeId, GradeEnum grade, Long gradeOrder) {
		super();
		this.gradeId = gradeId;
		this.grade = grade;
		this.gradeOrder = gradeOrder;
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

	@Override
	public String toString() {
		return "GradeDTO [gradeId=" + gradeId + ", grade=" + grade + ", gradeOrder=" + gradeOrder + "]";
	}
	
	
}
