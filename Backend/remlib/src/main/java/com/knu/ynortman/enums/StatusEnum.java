package com.knu.ynortman.enums;

import com.knu.ynortman.entity.Status;

public enum StatusEnum {
	NON_CIRCULATING (new Status(0, "available")),
	QUEUE (new Status(3, "queue")),
	AVAILABLE (new Status(2, "non-circulating"));
	
	private final Status status;
	private StatusEnum(final Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
}
