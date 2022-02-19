package com.resume.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resume.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
	public Collection<Education> findByEmployeeId(long employeeId);
}