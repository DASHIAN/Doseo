package com.lec.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int rno;
	@Column(insertable = false, updatable = false, columnDefinition = "timestamp default now()")
	public Date rentDate;
	@Column(insertable = false, updatable = true, columnDefinition = "timestamp")
	public Date returnDate;
	public int bno;
	public String memberId;
}
