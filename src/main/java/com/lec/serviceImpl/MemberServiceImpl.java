package com.lec.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.domain.Member;
import com.lec.persistence.MemberRepository;
import com.lec.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	public MemberRepository memberRepo;
	// 회원가입하기
	@Override
	public void registerMember(Member member) {
		memberRepo.save(member);
	}
	// 로그인 후 회원정보보기
	@Override
	public Member getMember(Member member) {
		Optional<Member> findMember = memberRepo.findById(member.getId());
		if(findMember.isPresent()) return findMember.get();
		else return null;
	}
	// 회원정보 수정하기
	@Override
	public void updateMember(Member member) {
		memberRepo.save(member);
	}
}
