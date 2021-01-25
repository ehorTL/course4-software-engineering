package com.knu.ynortman.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.LoanCard;
import com.knu.ynortman.entity.User;

public interface LoanCardRepository extends CrudRepository<LoanCard, Long> {
	Iterable<LoanCard> findByCatalogEntry(CatalogEntry catalogEntry);
	Optional<LoanCard> findByCatalogEntryAndUser(CatalogEntry catalogEntry, User user);
}
