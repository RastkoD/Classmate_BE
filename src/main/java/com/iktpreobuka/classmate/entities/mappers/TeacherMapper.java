package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.entities.dto.TeacherDTO;
import com.iktpreobuka.classmate.entities.enums.RoleEnum;
import com.iktpreobuka.classmate.repositories.UserRoleRepository;
import com.iktpreobuka.classmate.utils.UsernameUtil;

@Component
public class TeacherMapper {
    
    @Autowired
    UserRoleRepository roleRepository;
    
    @Autowired
    private UsernameUtil usernameUtil;

    public TeacherEntity toEntity(TeacherDTO dto) {
        if (dto == null) {
            return null;
        }
        
        String baseUsername = dto.getFirstName().toLowerCase() + "." + dto.getLastName().toLowerCase();
	    String username = usernameUtil.generateUniqueUsername(baseUsername);
        
        TeacherEntity entity = new TeacherEntity();
        entity.setUsername(username);
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDeleted(false);
        entity.setConsultations(dto.getConsultations());
        entity.setUserRole(roleRepository.findByRoleName(RoleEnum.TEACHER).get());

        return entity;
    }

    public TeacherDTO toDTO(TeacherEntity entity) {
        if (entity == null) {
            return null;
        }
        
        TeacherDTO dto = new TeacherDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDeleted(entity.isDeleted());
        dto.setConsultations(entity.getConsultations());
        
        return dto;
    }
}

