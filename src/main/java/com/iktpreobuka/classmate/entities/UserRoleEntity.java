package com.iktpreobuka.classmate.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iktpreobuka.classmate.entities.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "user_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userRole_sequence_generator")
	@SequenceGenerator(name = "userRole_sequence_generator", sequenceName = "userRole_sequence", allocationSize = 1)
	@Column(name = "id")
	@JsonProperty("ID")
	private long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleEnum roleName;
	
	@OneToMany(mappedBy = "userRole", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<UserAccountEntity> users;
	
	@Version
	@JsonIgnore
	private Integer version;

	public UserRoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public RoleEnum getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleEnum roleName) {
		this.roleName = roleName;
	}

	public List<UserAccountEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserAccountEntity> users) {
		this.users = users;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "UserRoleEntity [roleId=" + roleId + ", roleName=" + roleName + ", users=" + users + ", version="
				+ version + "]";
	}

	
}
