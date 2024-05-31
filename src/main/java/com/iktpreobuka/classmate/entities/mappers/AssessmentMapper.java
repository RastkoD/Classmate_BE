package com.iktpreobuka.classmate.entities.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.AssessmentEntity;
import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.dto.AssessmentDTO;
import com.iktpreobuka.classmate.services.CourseDao;
import com.iktpreobuka.classmate.services.StudentDao;
import com.iktpreobuka.classmate.services.TeacherDao;

@Component
public class AssessmentMapper {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentMapper.class);

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	public AssessmentEntity toEntity(AssessmentDTO dto) {

		try {
			if (dto == null) {
				return null;
			}

			AssessmentEntity entity = new AssessmentEntity();
			entity.setAssessmentId(dto.getAssessmentId());
			entity.setMark(dto.getMark());
			entity.setComment(dto.getComment());
			entity.setAssessmentType(dto.getAssessmentType());

			if (dto.getTeacherId() != null) {
				TeacherEntity teacher = teacherDao.getTeacherById(dto.getTeacherId());
				if (teacher != null) {
					entity.setTeacher(teacher);
				}
			}

			if (dto.getCourseId() != null) {
				CourseEntity course = courseDao.getCourseById(dto.getCourseId());
				if (course != null) {
					entity.setCourse(course);
				}
			}

			if (dto.getStudentId() != null) {
				StudentEntity student = studentDao.getStudentById(dto.getStudentId());
				if (student != null) {
					entity.setStudent(student);
				}
			}

			return entity;
		} catch (Exception e) {
			logger.error("Error mapping AssessmentDTO to AssessmentEntity: {}", e.getMessage());
			throw e;
		}
	}

	public AssessmentDTO toDTO(AssessmentEntity entity) {
		try {
			if (entity == null) {
				return null;
			}

			AssessmentDTO dto = new AssessmentDTO();
			dto.setAssessmentId(entity.getAssessmentId());
			dto.setMark(entity.getMark());
			dto.setComment(entity.getComment());
			dto.setAssessmentType(entity.getAssessmentType());

			if (entity.getTeacher() != null) {
				dto.setTeacherId(entity.getTeacher().getUserId());
				dto.setTeacherUsername(entity.getTeacher().getUsername());
			}

			if (entity.getCourse() != null) {
				dto.setCourseId(entity.getCourse().getCourseId());
				dto.setCourseName(entity.getCourse().getCourseName());
			}

			if (entity.getStudent() != null) {
				dto.setStudentId(entity.getStudent().getUserId());
				dto.setStudentUsername(entity.getStudent().getUsername());
			}

			return dto;
		} catch (Exception e) {
			logger.error("Error mapping AssessmentEntity to AssessmentDTO: {}", e.getMessage());
			throw e;
		}
	}
}
