package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.GradeEntity;
import com.iktpreobuka.classmate.entities.dto.GradeDTO;

@Component
public class GradeMapper {

public GradeDTO toDTO(GradeEntity entity) {
		
	GradeDTO dto = new GradeDTO();
		
		BeanUtils.copyProperties(entity, dto);
		
		return dto;
	}
	
	public GradeEntity toEntity(GradeDTO dto) {
		
		GradeEntity entity = new GradeEntity();
		
		BeanUtils.copyProperties(dto, entity);
		
		return entity;
	}
}
