package com.wy.life.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Project extends BaseModel{

	private Long userId;
	
	@Column(length = 20)
	@NotEmpty(message="项目名不能为空")
	private String name;
	
	private boolean dsc1;
	private boolean dsc2;
	private boolean dsc3;
	private boolean dsc4;
	private boolean dsc5;
	@Column(length = 100)
	private String fileName;
	@Column(columnDefinition="mediumblob")
	private byte[] fileContent;
	
	private int state;
	
}
