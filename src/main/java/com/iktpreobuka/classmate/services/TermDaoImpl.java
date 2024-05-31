package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.repositories.TermRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TermDaoImpl implements TermDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private TermRepository termRepository;

	@Override
	public List<TermEntity> getAllTerms() {
		return (List<TermEntity>) termRepository.findAll();
	}

	@Override
	public Optional<TermEntity> getTermById(Long termId) {
		return termRepository.findById(termId);
	}

	@Override
	public TermEntity createTerm(TermEntity term) {
		return termRepository.save(term);
	}

	@Override
	public TermEntity updateTerm(Long termId, TermEntity updatedTerm) {
		
		updatedTerm.setTermId(termId);
		
		return termRepository.save(updatedTerm);
	}

	@Override
	public void deleteTerm(Long termId) {
		
		termRepository.deleteById(termId);
		
	}
}
