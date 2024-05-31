package com.iktpreobuka.classmate.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.classmate.entities.TermEntity;
import com.iktpreobuka.classmate.entities.enums.TermNameEnum;

@Repository
public interface TermRepository extends CrudRepository<TermEntity, Long> {

	TermEntity findByTermName(TermNameEnum termName);

}
