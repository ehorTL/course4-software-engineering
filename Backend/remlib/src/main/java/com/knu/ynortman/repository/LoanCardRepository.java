package com.knu.ynortman.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.entity.CatalogEntry;
import com.knu.ynortman.entity.LoanCard;
import com.knu.ynortman.entity.LoanStatus;
import com.knu.ynortman.entity.User;

public interface LoanCardRepository extends CrudRepository<LoanCard, Long> {
	Iterable<LoanCard> findByCatalogEntryAndLoanStatus(CatalogEntry catalogEntry, LoanStatus loanStatus);
	Iterable<LoanCard> findByCatalogEntryAndLoanStatusAndNotified(CatalogEntry catalogEntry, LoanStatus loanStatus, boolean notified);
	Optional<LoanCard> findByCatalogEntryAndUserAndLoanStatus(CatalogEntry catalogEntry, User user, LoanStatus loanStatus);
}
