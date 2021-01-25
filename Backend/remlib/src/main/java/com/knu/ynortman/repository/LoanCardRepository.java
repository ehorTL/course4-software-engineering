package com.knu.ynortman.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.LoanCard;

public interface LoanCardRepository extends CrudRepository<LoanCard, Long> {
	Iterable<LoanCard> findByCatalogEntry(CatalogEntry catalogEntry);

}
