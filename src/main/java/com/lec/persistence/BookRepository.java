package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
		Page<Book> findByTitleContaining(String title, Pageable pageable);
	    Page<Book> findByWriterContaining(String writer, Pageable pageable);
	    Page<Book> findByContentContaining(String content, Pageable pageable);
		Book findByBno(int bno);
}
