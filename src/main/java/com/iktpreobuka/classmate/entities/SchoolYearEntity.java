package com.iktpreobuka.classmate.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "school_year")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolYearEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "schoolYear_sequence_generator")
	@SequenceGenerator(name = "schoolYear_sequence_generator", sequenceName = "schoolYear_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long schoolYearId;
	
	@Column(name = "start_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	@Column(name = "school_year")
	private String schoolYearName; // startDate.getYear + endDate.getYear

	@OneToMany(mappedBy = "schoolYear", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<ClassEntity> classes;
	
	@OneToMany(mappedBy = "schoolYear", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<TermEntity> terms;

	public SchoolYearEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getSchoolYearId() {
		return schoolYearId;
	}

	public void setSchoolYearId(Long schoolYearId) {
		this.schoolYearId = schoolYearId;
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

	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

	public List<TermEntity> getTerms() {
		return terms;
	}

	public void setTerms(List<TermEntity> terms) {
		this.terms = terms;
	}

	@Override
	public String toString() {
		return "SchoolYearEntity [schoolYearId=" + schoolYearId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", schoolYearName=" + schoolYearName + ", classes=" + classes + ", terms=" + terms + "]";
	}

	
}
