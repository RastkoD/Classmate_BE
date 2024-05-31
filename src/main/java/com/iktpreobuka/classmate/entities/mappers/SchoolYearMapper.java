package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.SchoolYearEntity;
import com.iktpreobuka.classmate.entities.dto.SchoolYearDTO;

@Component
public class SchoolYearMapper {
	
	public SchoolYearDTO toDTO(SchoolYearEntity entity) {
		
		SchoolYearDTO dto = new SchoolYearDTO();
		
		BeanUtils.copyProperties(entity, dto);
		
		return dto;
	}
	
	
	public SchoolYearEntity toEntity(SchoolYearDTO dto) {
		
		SchoolYearEntity entity = new SchoolYearEntity();
		
		BeanUtils.copyProperties(dto, entity);
		
		return entity;
	}
}
