package com.lec.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.domain.Book;
import com.lec.persistence.BookRepository;
import com.lec.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private BookRepository bookRepo;
	
	@Override
	public void insertBook(Book book) {
		bookRepo.save(book);
	}

	@Override
	public void updateBook(Book book) {
		 // 기존의 도서 정보를 가져옵니다.
		Book existingBook = bookRepo.findById(book.getBno()).orElse(null);
	     if (existingBook != null) {
	         // 수정할 필드 값들을 새로운 값으로 업데이트합니다.
	    	 existingBook.setTitle(book.getTitle());
	    	 existingBook.setWriter(book.getWriter());
	    	 existingBook.setFileName(book.getFileName());
	    	 existingBook.setContent(book.getContent());
	    	 existingBook.setRentId(book.getRentId());
	    	 bookRepo.save(existingBook);
	     }
	}
}
