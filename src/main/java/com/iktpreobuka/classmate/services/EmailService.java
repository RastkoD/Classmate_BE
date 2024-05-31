package com.iktpreobuka.classmate.services;

import com.iktpreobuka.classmate.entities.AssessmentEntity;

public interface EmailService {
	
	void sendAssessmentEmail(AssessmentEntity mark);
	
	String createAssessTableHtml(AssessmentEntity mark);
}
