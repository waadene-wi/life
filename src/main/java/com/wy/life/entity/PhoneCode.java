package com.wy.life.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class PhoneCode extends BaseModel {
	@Column(columnDefinition="varchar(20) NULL")
	private String phone;
	@Column(columnDefinition="varchar(255) NULL")
	private String verifyCode;
	@Column(columnDefinition="varchar(32) NULL")
	private String resultCode;
}
