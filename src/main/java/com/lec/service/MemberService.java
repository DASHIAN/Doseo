package com.lec.service;

import com.lec.domain.Member;

public interface MemberService {
	
	void registerMember(Member member); //회원가입
	Member getMember(Member member); // 회원정보 가져오기
	void updateMember(Member member); //회원정보 수정하기
}
