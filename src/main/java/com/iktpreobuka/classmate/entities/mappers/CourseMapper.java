package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.CourseEntity;
import com.iktpreobuka.classmate.entities.dto.CourseDTO;

@Component
public class CourseMapper {
	
	public static CourseEntity toEntity(CourseDTO dto) {
		if(dto == null) {
			return null;
		}
		
		CourseEntity entity = new CourseEntity();
		entity.setCourseName(dto.getCourseName());
		entity.setWeekUnits(dto.getWeekUnits());
		
		return entity;
	}
	
	public static CourseDTO toDTO(CourseEntity entity) {
		if(entity == null) {
			return null;
		}
		
		CourseDTO dto = new CourseDTO();
		dto.setCourseName(entity.getCourseName());
		dto.setWeekUnits(entity.getWeekUnits());
		
		return dto;
	}
}
