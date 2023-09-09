package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lec.domain.Member;
import com.lec.service.MemberService;

@Controller
@SessionAttributes("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//로그인화면
	@GetMapping("/login")
	public String loginView() {
		return "/login/login";
	}
	//로그인하기
	@PostMapping("/login")
	public String login(Member member, Model model) {
		Member findMember = memberService.getMember(member);
		if(findMember != null && findMember.getPassword().equals(member.getPassword())) {
			model.addAttribute("member", findMember); // session과 request 영역에 동시에 저장
			return "member/getMemberInfo";
		}else {
			return "redirect:login";
		}
	}
	//로그아웃하기
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:login";
	}
	//회원가입화면
	@GetMapping("registerMember")
	public String registerMemberForm(Member member) {
		return "join/registerMember";
	}
	//회원가입하기
	@PostMapping("registerMember")
	public String registerMember(Member member) {

		if(member.getId() == null) {
			return "redirect:registerMember";
		}
		member.setRole("USER");
		memberService.registerMember(member);

		return "/login/login";
	}
	//내정보보기
	@GetMapping("/memberInfo")
	public String memberInfo() {
		return "member/memberInfo";
	}
	//회원정보수정화면
	@GetMapping("updateMember")
	public String updateMember(Member member, Model model) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		model.addAttribute("member", memberService.getMember(member));
		return "member/updateMember";
	}
	//회원정보수정
	@PostMapping("updateMember")
	public String updateMember(Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		member.setRole(member.getRole() != null ? "ADMIN" : "USER");
		memberService.updateMember(member);
		
	      return "redirect:login";
	}
}
