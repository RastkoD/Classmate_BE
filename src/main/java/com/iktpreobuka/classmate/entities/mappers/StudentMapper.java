package com.iktpreobuka.classmate.entities.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.entities.dto.StudentDTO;
import com.iktpreobuka.classmate.entities.enums.RoleEnum;
import com.iktpreobuka.classmate.repositories.UserRoleRepository;
import com.iktpreobuka.classmate.utils.UsernameUtil;

@Component
public class StudentMapper {
	
	@Autowired
	UserRoleRepository roleRepository;
	
	@Autowired
	private UsernameUtil usernameUtil;
	
	@Autowired
    private GuardianMapper guardianMapper;

    public StudentEntity toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        String baseUsername = dto.getFirstName().toLowerCase() + "." + dto.getLastName().toLowerCase();
	    String username = usernameUtil.generateUniqueUsername(baseUsername);
        StudentEntity entity = new StudentEntity();
        entity.setUsername(username);
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDeleted(false);
        entity.setJmbg(dto.getJmbg());
        entity.setUserRole(roleRepository.findByRoleName(RoleEnum.STUDENT).get());
        return entity;
    }

    public StudentDTO toDTO(StudentEntity entity) {
        if (entity == null) {
            return null;
        }
        StudentDTO dto = new StudentDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDeleted(entity.isDeleted());
        dto.setJmbg(entity.getJmbg());
        
        if(entity.getGuardian() != null) {
            dto.setGuardian(guardianMapper.toDTO(entity.getGuardian()));
        }
        
        if(entity.getAttendingClass() != null) {
            dto.setStudentClass(ClassMapper.toDTO(entity.getAttendingClass()));
        }
        
        return dto;
    }
}
