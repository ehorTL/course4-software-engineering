package com.knu.ynortman.constants;

import org.springframework.stereotype.Component;

import com.knu.ynortman.entity.LoanStatus;
import com.knu.ynortman.repository.LoanStatusRepository;

@Component
public class LoanStatusConst {
	private final LoanStatusRepository loanStatusRepo;
	
	public LoanStatusConst(LoanStatusRepository loanStatusRepo) {
		this.loanStatusRepo = loanStatusRepo;
		REQUESTED = this.loanStatusRepo.findById(0).get();
		CHECKED_OUT = this.loanStatusRepo.findById(1).get();
		CHECKED_IN = this.loanStatusRepo.findById(2).get();
	}
	
	public final LoanStatus REQUESTED;
	public final LoanStatus CHECKED_OUT;
	public final LoanStatus CHECKED_IN;
}
