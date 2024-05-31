package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.entities.dto.TermDTO;

@Component
public class TermMapper {
	
	public TermDTO toDTO(TermEntity entity) {
		
		TermDTO dto = new TermDTO();
		
		BeanUtils.copyProperties(entity, dto);
		
		return dto;
	}
	
	public TermEntity toEntity(TermDTO dto) {
		
		TermEntity entity = new TermEntity();
		
		BeanUtils.copyProperties(dto, entity);
		
		return entity;
	}
}
