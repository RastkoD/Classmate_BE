package com.iktpreobuka.classmate.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.classmate.entities.TermEntity;

public interface TermDao {
	
	List<TermEntity> getAllTerms();
	
	Optional<TermEntity> getTermById(Long termId);
	
	TermEntity createTerm(TermEntity term);
	
	TermEntity updateTerm(Long termId, TermEntity updatedTerm);
	
	void deleteTerm(Long termId);
}
