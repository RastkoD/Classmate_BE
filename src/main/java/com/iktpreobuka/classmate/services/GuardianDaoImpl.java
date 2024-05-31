package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.repositories.GuardianRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GuardianDaoImpl implements GuardianDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private GuardianRepository guardianRepository;

	@Override
	public List<GuardianEntity> getAllGuardians() {
		return (List<GuardianEntity>) guardianRepository.findAll();
	}

	@Override
	public GuardianEntity getGuardianById(Long guardianId) {
		return guardianRepository.findById(guardianId).orElse(null);
	}

	@Override
	public GuardianEntity createGuardian(GuardianEntity newGuardian) {
		return guardianRepository.save(newGuardian);
	}

	@Override
	public GuardianEntity updateGuardian(Long guardianId,GuardianEntity updatedGuardian) {
		if(!guardianRepository.existsById(guardianId)) {
			return null;
		}
		
		GuardianEntity existingGuardian = guardianRepository.findById(guardianId).orElse(null);
	
		if (existingGuardian != null) {
			updatedGuardian.setVersion(existingGuardian.getVersion());
	    }
		
		updatedGuardian.setUserId(guardianId);
		return guardianRepository.save(updatedGuardian);
	}

	@Override
	public void deleteGuardian(Long guardianId) {
		guardianRepository.deleteById(guardianId);
		
	}
}
