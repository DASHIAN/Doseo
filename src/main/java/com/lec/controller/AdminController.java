package com.lec.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Book;
import com.lec.domain.Member;
import com.lec.domain.Rent;
import com.lec.service.AdminService;
import com.lec.service.BookService;
import com.lec.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@SessionAttributes("member")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private BookService bookService;
	
	@Value("${path.upload}")
	public String uploadFolder;
	
	// 책정보입력화면
	@GetMapping("/insertBook")
	public String insertBookView(@ModelAttribute("member") Member member) {
		if (member.getId() == null) {
			return "redirect:login";
		}
		return "admin/insertBook";
	}
	// 책정보입력
	@PostMapping("/insertBook")
	public String insertBook(@ModelAttribute("member") Member member, Book book) throws IOException {
		if (member.getId() == null) {
			return "redirect:login";
		}	
		// 파일업로드
		MultipartFile uploadFile = book.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(uploadFolder + fileName));
			book.setFileName(fileName);
		}	
		adminService.insertBook(book);
		return "library/bookInfo";
	}
	// 책정보수정페이지
	@GetMapping("/updateBook")
	public String updateBook(@ModelAttribute("member") Member member, Book book, Model model) {
		if (member.getId() == null) {
			return "redirect:login";
		}
		model.addAttribute("book", bookService.bookInfo(book));
		model.addAttribute("uploadPath", uploadFolder);
		return "admin/updateBook";
	}
	// 책정보수정하기
	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute("member") Member member, @ModelAttribute("book")Book book, HttpServletRequest request) throws Exception{
		if (member.getId() == null) {
			return "redirect:login";
		}
		MultipartFile uploadFile = book.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(uploadFolder + fileName));
			book.setFileName(fileName);
		}
	       String title = request.getParameter("title"); 
	       book.setTitle(title); 
	       String content = request.getParameter("content"); 
	       book.setContent(content); 
	       String writer = request.getParameter("writer"); 
	       book.setWriter(writer); 
	       String rentId = request.getParameter("rentId"); 
	       book.setRentId(rentId); 
	     
		adminService.updateBook(book);
		return "redirect:bookList";
	}
	// 해당 도서 대출 내역
	@RequestMapping("rentList")
	public String rentList(@ModelAttribute("member") Member member, Rent rent, Model model) {
		if (member.getId() == null) {
			return "redirect:login";
		}
		List<Rent> rentList = bookService.rentList(rent.getBno());
		model.addAttribute("rentList", rentList);
		return "admin/rentList";
	}
}
