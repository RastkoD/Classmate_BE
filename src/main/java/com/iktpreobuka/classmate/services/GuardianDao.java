package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.GuardianEntity;

public interface GuardianDao {
	List<GuardianEntity> getAllGuardians();
	
	GuardianEntity getGuardianById(Long guardianId);
	
	GuardianEntity createGuardian(GuardianEntity newGuardian);
	
	GuardianEntity updateGuardian(Long guardianId, GuardianEntity updatedGuardian);
	
	void deleteGuardian(Long guardianId);
}
