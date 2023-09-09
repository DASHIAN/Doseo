package com.lec.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Book;
import com.lec.domain.Rent;
import com.lec.persistence.BookRepository;
import com.lec.persistence.RentRepository;
import com.lec.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private RentRepository rentRepo;
	// 도서정보보기
	@Override
	public Book bookInfo(Book book) {
		Optional<Book> findBook = bookRepo.findById(book.getBno());
		if(findBook.isPresent())
			return findBook.get();
		else return null;
	}
	// 도서리스트
	@Override
	public Page<Book> bookList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("title")) {
			return bookRepo.findByTitleContaining(searchWord, pageable);
		} else if(searchType.equalsIgnoreCase("writer")) {
			return bookRepo.findByWriterContaining(searchWord, pageable);
		} else {
			return bookRepo.findByContentContaining(searchWord, pageable);
		}
	}
	// 도서대여
	@Override
	public void rentBook(int bno, String rentStatus, Rent rent) {
		Book book = bookRepo.findByBno(bno);
		book.setRentId(rentStatus);
		bookRepo.save(book);
		rentRepo.save(rent);
	}
	// 도서반납
	@Override
	public void returnBook(int bno, String rentStatus) {
		Book book = bookRepo.findByBno(bno);
		book.setRentId(rentStatus);
		
	        List<Rent> rentList = rentRepo.findTopByBnoOrderByRentDateDesc(bno);
	        if (!rentList.isEmpty()) {
	            Rent rent = rentList.get(0);
	            rent.setReturnDate(new Date());
	            rentRepo.save(rent);
	        }
		bookRepo.save(book);
	}
	// 도서대출리스트
	@Override
	public List<Rent> rentList(int bno) {
		return rentRepo.findByBno(bno);
	}
	// 도서반납처리를 위한 도서대출정보 찾기
	@Override
	public Rent rentInfo(Rent rent) {
		Optional<Rent> findRent = rentRepo.findById(rent.getBno());
		if(findRent.isPresent())
			return findRent.get();
		else return null;
	}
}
