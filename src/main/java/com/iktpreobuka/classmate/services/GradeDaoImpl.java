package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.repositories.GradeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GradeDaoImpl implements GradeDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private GradeRepository gradeRepository;

	@Override
	public List<GradeEntity> getAllGrades() {
		return (List<GradeEntity>) gradeRepository.findAll();
	}

	@Override
	public Optional<GradeEntity> getGradeById(Long gradeId) {
		return gradeRepository.findById(gradeId);
	}

	@Override
	public GradeEntity createGrade(GradeEntity grade) {
		return gradeRepository.save(grade);
	}

	@Override
	public GradeEntity updateGrade(Long gradeId, GradeEntity updatedGrade) {
		updatedGrade.setGradeId(gradeId);
		
		return gradeRepository.save(updatedGrade);
	}

	@Override
	public void deleteGrade(Long gradeId) {
		gradeRepository.deleteById(gradeId);
		
	}
}
