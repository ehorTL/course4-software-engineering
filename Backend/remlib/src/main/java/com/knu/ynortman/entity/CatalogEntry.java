package com.knu.ynortman.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class CatalogEntry {
	@Id
	@GeneratedValue(generator = "catalog_entry_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "catalog_entry_id_seq", sequenceName = "catalog_entry_id_seq", allocationSize = 2)
	private long id;
	private String itemNumber;
	@ManyToOne
	private Publication publication;
	@ManyToOne
	private Library library;
	@ManyToOne
	private Status status;
	private Date avlblFrom;
	private int copiesNumber;
	private int copiesAvlbl;
	private int loanDays;
}
