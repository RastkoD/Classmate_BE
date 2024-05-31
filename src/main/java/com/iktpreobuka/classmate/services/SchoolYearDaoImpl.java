package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.SchoolYearEntity;
import com.iktpreobuka.classmate.repositories.SchoolYearRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SchoolYearDaoImpl implements SchoolYearDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private SchoolYearRepository schoolYearRepository;

	@Override
	public List<SchoolYearEntity> getAllSchoolYears() {
		
		return (List<SchoolYearEntity>) schoolYearRepository.findAll();
	}

	@Override
	public Optional<SchoolYearEntity> getSchoolYearById(Long schoolYearId) {
		
		return schoolYearRepository.findById(schoolYearId);
	}

	@Override
	public SchoolYearEntity createSchoolYear(SchoolYearEntity schoolYear) {
		
		return schoolYearRepository.save(schoolYear);
	}

	@Override
	public SchoolYearEntity updateSchoolYear(Long schoolYearId, SchoolYearEntity updatedSchoolYear) {
		
		updatedSchoolYear.setSchoolYearId(schoolYearId);
		
		return schoolYearRepository.save(updatedSchoolYear);
	}

	@Override
	public void deleteSchoolYear(Long schoolYearId) {
		
		schoolYearRepository.deleteById(schoolYearId);
		
	}

	@Override
	public SchoolYearEntity findSchoolYearByName(String schoolYearName) {
		
		return schoolYearRepository.findBySchoolYearName(schoolYearName);
	}
}
