package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.dto.ClassDTO;

@Component
public class ClassMapper {
	
	public static ClassEntity toEntity(ClassDTO dto) {
		if(dto == null) {
			return null;
		}
		
		ClassEntity entity = new ClassEntity();
		entity.setClassId(dto.getClassId());
		entity.setClassName(dto.getClassName());

		return entity;
	}
	
	public static ClassDTO toDTO(ClassEntity entity) {
		if(entity == null) {
			return null;
		}
		
		ClassDTO dto = new ClassDTO();
		dto.setClassId(entity.getClassId());
		dto.setClassName(entity.getClassName());
		
		return dto;
	}

}
