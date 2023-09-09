package com.lec.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer>{
	
	@Transactional
	@Modifying
	@Query("SELECT r FROM Rent r WHERE r.bno = :bno ORDER BY r.rentDate DESC")
	List<Rent> findByBno(int bno);
	
	List<Rent> findTopByBnoOrderByRentDateDesc(int bno);
}
