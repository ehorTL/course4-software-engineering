package com.knu.ynortman.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.knu.ynortman.util.EmailUtil;

import lombok.Data;

@Data
@Entity
public class LoanCard {
	@Id
	@GeneratedValue(generator = "loan_card_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loan_card_id_seq", sequenceName = "loan_card_id_seq", allocationSize = 2)
	private long id;
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;
	@ManyToOne(cascade = CascadeType.MERGE)
	private CatalogEntry catalogEntry;
	@ManyToOne(cascade = CascadeType.MERGE)
	private LoanStatus loanStatus;
	@Column(name = "checked_out_date")
	private Date checkedOut;
	private Date loanUntil;
	private Date avlblFrom;
	private String notes;
	private boolean notified;
	
	public void notification() {
		this.notified = true;
	}
}
