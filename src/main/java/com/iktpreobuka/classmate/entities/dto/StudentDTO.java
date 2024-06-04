package com.iktpreobuka.classmate.entities.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class StudentDTO extends UserAccountDTO {
	
	private Long studentId;

	@NotNull(message = "Personal ID number must be provided.")
	@Pattern(regexp = "^\\d{8}$", message = "Personal ID number must be exactly 8 characters long.")
    private String jmbg;
    private GuardianDTO guardian;
    private ClassDTO studentClass;

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentDTO(Long studentId,
			@NotNull(message = "Personal ID number must be provided.") @Pattern(regexp = "^\\d{8}$", message = "Personal ID number must be exactly 8 characters long.") String jmbg,
			GuardianDTO guardian, ClassDTO studentClass) {
		super();
		this.studentId = studentId;
		this.jmbg = jmbg;
		this.guardian = guardian;
		this.studentClass = studentClass;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public GuardianDTO getGuardian() {
		return guardian;
	}

	public void setGuardian(GuardianDTO guardian) {
		this.guardian = guardian;
	}

	public ClassDTO getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(ClassDTO studentClass) {
		this.studentClass = studentClass;
	}
    
	
	
}

