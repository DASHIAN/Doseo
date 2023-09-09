package com.lec.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Member {
	
	@Id
	@Column(insertable = false, updatable = false, nullable = false)
	public String id;
	@Column(nullable = false)
	public String password;
	@Column(nullable = false)
	public String name;
	@Column(nullable = false)
	public String birth;
	@Column(nullable = false)
	public String phone;
	@Column(nullable = false)
	public String role;
}

