package com.lec.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
