package com.knu.ynortman.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class User {
	@Id
	private String email;
	private String surname;
	private String name;
	private String patronymic;
	private Date birthday;
	private long phone;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Role role;
	private float fee;
}
