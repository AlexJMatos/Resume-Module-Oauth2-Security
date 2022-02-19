package com.resume.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.resume.model.Resume;

public class ResumeSpecification implements Specification<Resume> {

	private static final long serialVersionUID = -8022794057103014727L;
	private SearchCriteria searchCriteria;

	public ResumeSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		/* Searching inside resume*/
		if (searchCriteria.getOperation().equals("~")) {
			query.distinct(true);
			return criteriaBuilder.like(root.get(searchCriteria.getKey()),
					"%" + searchCriteria.getValue().toString() + "%");
		} 
		
		/* Searching inside resume.technologies */
		else if (searchCriteria.getOperation().equals("@")) {
			query.distinct(true);
			return criteriaBuilder.like(root.join("technologies").get(searchCriteria.getKey()),
					"%" + searchCriteria.getValue().toString() + "%");
		} 
		
		/* Searching inside resume.education */
		else if (searchCriteria.getOperation().equals("?")) {
			query.distinct(true);
			return criteriaBuilder.like(root.join("education").get(searchCriteria.getKey()),
					"%" + searchCriteria.getValue().toString() + "%");
		}
		
		/* Searching inside resume.work-experience */
		else if (searchCriteria.getOperation().equals("/")) {
			query.distinct(true);
			return criteriaBuilder.like(root.join("workExperience").get(searchCriteria.getKey()),
					"%" + searchCriteria.getValue().toString() + "%");
		}
		return null;
	}
}
