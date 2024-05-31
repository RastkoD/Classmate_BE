package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.classmate.utils.UniqueEmail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "guardian")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GuardianEntity extends UserAccountEntity {

	@UniqueEmail
	@Column(name = "email", nullable = false)
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;

	//private String phoneNumber;
	
	@OneToMany(mappedBy = "guardian", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<StudentEntity> wards;

	public GuardianEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<StudentEntity> getWards() {
		return wards;
	}

	public void setWards(List<StudentEntity> wards) {
		this.wards = wards;
	}

	@Override
	public String toString() {
		return "GuardianEntity [email=" + email + ", wards=" + wards + "]";
	}
	
	
}
