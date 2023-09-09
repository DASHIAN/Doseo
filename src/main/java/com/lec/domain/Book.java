package com.lec.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Book {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "bno", insertable = false, updatable = false)
	    private int bno;
	    @Column(nullable = false)
	    private String title;
	    @Column(nullable = false)
	    private String writer;
	    @Column(nullable = false)
	    private String content;
	    @Column(nullable = false)
	    private String rentId;
	    private String fileName;
	    @Transient
	    private MultipartFile uploadFile;
}
