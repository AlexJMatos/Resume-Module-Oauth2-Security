package com.resume.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.resume.model.Resume;

@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

	public boolean existsByEmail(String email);
	
	public boolean existsByPhoneNumber(String phoneNumber);
	
	public Resume findByEmailAndPhoneNumber(String email, String phoneNumber);
}
