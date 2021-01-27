package com.knu.ynortman.constants;

import org.springframework.stereotype.Component;

import com.knu.ynortman.entity.Status;
import com.knu.ynortman.repository.StatusRepository;

@Component
public class StatusConst {
private final StatusRepository statusRepo;
	
	public StatusConst(StatusRepository statusRepo) {
		this.statusRepo = statusRepo;
		AVAILABLE = this.statusRepo.findById(0).get();
		NON_CIRCULATING = this.statusRepo.findById(2).get();
		QUEUE = this.statusRepo.findById(3).get();
	}
	
	public final Status AVAILABLE;
	public final Status NON_CIRCULATING;
	public final Status QUEUE;
}
