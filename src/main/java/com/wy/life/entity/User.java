package com.wy.life.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class User  extends BaseModel{
	
	@Column(length = 20)
	private String phone;
	@Column(length = 20)
	private String name;
	@Column(length = 20)
	private String company;
	@Column(length = 20)
	private String job;
	@Column(length = 20)
	private String contact;
	@Column(length = 20)
	private String email;
	@Column(columnDefinition = "varchar(20) null")
	private Role role;
}
