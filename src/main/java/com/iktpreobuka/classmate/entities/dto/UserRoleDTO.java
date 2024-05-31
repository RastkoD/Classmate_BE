package com.iktpreobuka.classmate.entities.dto;

import com.iktpreobuka.classmate.entities.enums.RoleEnum;

public class UserRoleDTO {
	
	private Long roleId;
	private RoleEnum roleName;
	
	public UserRoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRoleDTO(Long roleId, RoleEnum roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleEnum getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleEnum roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserRoleDTO [roleId=" + roleId + "]";
	}
	
	
}
