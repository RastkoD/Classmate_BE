package com.iktpreobuka.classmate.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iktpreobuka.classmate.entities.enums.TermNameEnum;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "term")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TermEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "term_sequence_generator")
	@SequenceGenerator(name = "term_sequence_generator", sequenceName = "term_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private Long termId;
	
	@Column(name = "start_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "term_name")
	private TermNameEnum termName;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "school_year")
	private SchoolYearEntity schoolYear;
	
	@OneToMany(mappedBy = "term", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<CourseEntity> courses;

	public TermEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
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

	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	public List<CourseEntity> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "TermEntity [termId=" + termId + ", startDate=" + startDate + ", endDate=" + endDate + ", termName="
				+ termName + ", schoolYear=" + schoolYear + ", courses=" + courses + "]";
	}

	
}
