package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.GuardianEntity;
import com.iktpreobuka.classmate.entities.dto.GuardianDTO;
import com.iktpreobuka.classmate.entities.enums.RoleEnum;
import com.iktpreobuka.classmate.repositories.UserRoleRepository;
import com.iktpreobuka.classmate.utils.UsernameUtil;

@Component
public class GuardianMapper {
	
	@Autowired
	private UserRoleRepository roleRepository;
	
	@Autowired
	private UsernameUtil usernameUtil;
	
	public GuardianEntity toEntity(GuardianDTO dto) {
		if(dto == null) {
			return null;
		}
		
		String baseUsername = dto.getFirstName().toLowerCase() + "." + dto.getLastName().toLowerCase();
		String username = usernameUtil.generateUniqueUsername(baseUsername);
		
		GuardianEntity entity = new GuardianEntity();
		
		entity.setUsername(username);
		entity.setPassword(dto.getPassword());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setDeleted(false);
		entity.setUserRole(roleRepository.findByRoleName(RoleEnum.GUARDIAN).get());
		
		return entity;
	}
	
	public GuardianDTO toDTO(GuardianEntity entity) {
		if(entity == null) {
			return null;
		}
		
		GuardianDTO dto = new GuardianDTO();
		dto.setUsername(entity.getUsername());
		dto.setPassword(entity.getPassword());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		
		return dto;
	}
	
	
	
}
