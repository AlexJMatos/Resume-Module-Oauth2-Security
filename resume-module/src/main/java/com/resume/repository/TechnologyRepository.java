package com.resume.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resume.model.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
	public Collection<Technology> findByEmployeeId(long employeeId);
}
