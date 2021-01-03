package com.knu.ynortman.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class Library {
	@Id
	@GeneratedValue(generator = "library_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "library_id_seq", sequenceName = "library_id_seq", allocationSize = 2)
	private int id;
	private String name;
	private String address;
}
