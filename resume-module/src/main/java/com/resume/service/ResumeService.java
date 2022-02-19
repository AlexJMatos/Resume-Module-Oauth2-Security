package com.resume.service;

import org.springframework.data.domain.Pageable;

import com.resume.dto.PagedResult;
import com.resume.dto.ResumeDTO;
import com.resume.dto.ResumePatchDTO;
import com.resume.enums.SearchBy;

public interface ResumeService {
	public PagedResult<ResumeDTO> findAll(String search, SearchBy searchBy, Pageable pageable);

	public ResumeDTO findById(long id);

	public ResumeDTO save(ResumeDTO request);

	public ResumeDTO update(long id, ResumeDTO request);
	
	public ResumeDTO patch(long id, ResumePatchDTO request);
	
}
