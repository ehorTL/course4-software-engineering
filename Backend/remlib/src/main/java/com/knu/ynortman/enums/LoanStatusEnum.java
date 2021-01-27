package com.knu.ynortman.enums;

import com.knu.ynortman.entity.LoanStatus;

public enum LoanStatusEnum {
	REQUESTED (new LoanStatus(0, "requested")),
	CHECKED_OUT (new LoanStatus(1, "checked out")),
	CHECKED_IN (new LoanStatus(2, "checked in"));
	
	private final LoanStatus status;
	private LoanStatusEnum(final LoanStatus status) {
		this.status = status;
	}
	
	public LoanStatus getStatus() {
		return this.status;
	}
}
