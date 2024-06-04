package com.iktpreobuka.classmate.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.AssessmentEntity;
import com.iktpreobuka.classmate.entities.GuardianEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private AssessmentDao assessDao;

	@Override
	public void sendAssessmentEmail(AssessmentEntity assessment) {
		try {
			GuardianEntity guardian = assessDao.getStudentGuardian(assessment);
			
			if (guardian == null) {
				System.out.println("Warning: No guardian found for student with assessment ID " + assessment.getAssessmentId());
				return;
			}
			
			String guardianEmail = guardian.getEmail();
			if (guardianEmail == null || guardianEmail.isEmpty()) {
				System.out.println("Warning: No email found for guardian of student with assessment ID " + assessment.getAssessmentId());
				return;
			}

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setTo(guardianEmail);
			helper.setSubject("New Assessment");
			String voucherTable = createAssessTableHtml(assessment);
			helper.setText(voucherTable, true);
			
			emailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while trying to send the email", e);
		}
	}

	@Override
	public String createAssessTableHtml(AssessmentEntity assessment) {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		String formattedDate = date.format(formatter);

		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>Assessment Details</title>");
		sb.append("<style>");
		sb.append("table.GeneratedTable {");
		sb.append("width: 90%;");
		sb.append("background-color: hsl(0deg, 0%, 95%);");
		sb.append("border-collapse: collapse;");
		sb.append("border-width: 2px;");
		sb.append("border-color: #0d0d0d;");
		sb.append("border-style: solid;");
		sb.append("color: #0d0d0d;");
		sb.append("}");

		sb.append("table.GeneratedTable td, table.GeneratedTable th {");
		sb.append("border-width: 2px;");
		sb.append("border-color: #0d0d0d;");
		sb.append("border-style: solid;");
		sb.append("padding: 3px;");
		sb.append("}");

		sb.append("table.GeneratedTable thead {");
		sb.append("background-color: #0d0d0d;");
		sb.append("color: hsl(0deg, 0%, 95%);");
		sb.append("}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");

		sb.append("<table class=\"GeneratedTable\">");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th>Student</th>");
		sb.append("<th>Course</th>");
		sb.append("<th>Mark</th>");
		sb.append("<th>Teacher</th>");
		sb.append("<th>Date:</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td>" + assessDao.getStudentFromAssess(assessment).getFirstName()
				+ " " + assessDao.getStudentFromAssess(assessment).getLastName() + "</td>");
		sb.append("<td>" + assessDao.getCourseFromAssess(assessment).getCourseName() + "</td>");
		sb.append("<td>" + assessment.getMark().toString() + "</td>");
		sb.append("<td>" + assessDao.getTeacherFromAssess(assessment).getFirstName()
				+ " " + assessDao.getTeacherFromAssess(assessment).getLastName() + "</td>");
		sb.append("<td>" + formattedDate + "</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}    
}
