package com.iktpreobuka.classmate.entities.dto;

public class ClassDTO {
	private Long classId;
	private String className;
	private Long schoolYearId;
	private Long gradeId;
	private Long homeroomTeacherId;
	
	public ClassDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassDTO(Long classId, String className, Long schoolYearId, Long gradeId, Long homeroomTeacherId) {
		super();
		this.classId = classId;
		this.className = className;
		this.schoolYearId = schoolYearId;
		this.gradeId = gradeId;
		this.homeroomTeacherId = homeroomTeacherId;
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

	public Long getSchoolYearId() {
		return schoolYearId;
	}

	public void setSchoolYearId(Long schoolYearId) {
		this.schoolYearId = schoolYearId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getHomeroomTeacherId() {
		return homeroomTeacherId;
	}

	public void setHomeroomTeacherId(Long homeroomTeacherId) {
		this.homeroomTeacherId = homeroomTeacherId;
	}

	@Override
	public String toString() {
		return "ClassDTO [classId=" + classId + ", className=" + className + ", schoolYearId=" + schoolYearId
				+ ", gradeId=" + gradeId + ", homeroomTeacherId=" + homeroomTeacherId + "]";
	}
	
	
}
