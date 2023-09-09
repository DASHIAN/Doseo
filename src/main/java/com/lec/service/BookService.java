package com.lec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lec.domain.Book;
import com.lec.domain.Rent;

public interface BookService {

	Book bookInfo(Book book);
	Page<Book> bookList(Pageable pageable, String searchType, String searchWord);
	void rentBook(int bno, String rentStatus, Rent rent);
	void returnBook(int bno, String rentStatus);
	List<Rent> rentList(int bno);
	Rent rentInfo(Rent rent);
}
