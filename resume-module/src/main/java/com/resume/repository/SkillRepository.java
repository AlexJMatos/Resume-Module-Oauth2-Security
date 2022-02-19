package com.resume.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resume.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
	public Collection<Skill> findByEmployeeId(long employeeId);
}
