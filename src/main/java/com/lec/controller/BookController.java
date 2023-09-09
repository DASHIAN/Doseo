package com.lec.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lec.domain.Book;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.domain.Rent;
import com.lec.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"member","pagingInfo"})
public class BookController {
	@Autowired
	private BookService bookService;
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}	
	//도서리스트
	@RequestMapping("/bookList")
	public String getBoardList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="title") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("bno").descending());
		Page<Book> pagedResult = bookService.bookList(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
	
		pagingInfo.setCurPage(curPage);
		pagingInfo.setTotalRowCount(totalRowCount);
		pagingInfo.setTotalPageCount(totalPageCount);
		pagingInfo.setStartPage(startPage);
		pagingInfo.setEndPage(endPage);
		pagingInfo.setSearchType(searchType);
		pagingInfo.setSearchWord(searchWord);
		pagingInfo.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfo", pagingInfo);

		model.addAttribute("pagedResult", pagedResult);
		model.addAttribute("pageable", pageable);
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);	
		
		List<Book> bookList = pagedResult.getContent();
		return "library/bookList";
	}
	
	//책정보보기
	@GetMapping("/bookInfo")
	public String getBook(@ModelAttribute("member") Member member, @ModelAttribute("book")Book book, Rent rent, Model model) throws Exception {
		   if (member.getId() == null) {
		       return "redirect:/login";
		    }
		    model.addAttribute("book", bookService.bookInfo(book));
		    model.addAttribute("rent", bookService.rentInfo(rent)); // 대여 정보를 모델에 추가
		    return "library/bookInfo";
		}
	//대여하기
	@PostMapping("/rentBook")
	public String rentBook(@ModelAttribute("member") Member member, Book book, Rent rent, HttpServletRequest request, Model model) {
		if (member.getId() == null) {
		       return "redirect:/login";
		    }
		String bnoString = request.getParameter("bno");
		Integer bno = Integer.valueOf(bnoString);
		
		rent.setMemberId(member.getId());
		rent.setBno(bno);
		
		String rentStatus = request.getParameter("rentStatus");
		book.setRentId(rentStatus);
		
		bookService.rentBook(bno, rentStatus, rent);
		
		return "redirect:bookList";
	}
	//반납하기
	@PostMapping("/returnBook")
	public String returnBook(@ModelAttribute("member") Member member, Book book, HttpServletRequest request, Model model) {
	    if (member.getId() == null) {
	        return "redirect:/login";
	    }
	    String bnoString = request.getParameter("bno");
	    Integer bno = Integer.valueOf(bnoString);

	    String rentStatus = request.getParameter("rentStatus");
	    book.setRentId(rentStatus);

	    bookService.returnBook(bno, rentStatus);

	    return "redirect:bookList";
	}
}
