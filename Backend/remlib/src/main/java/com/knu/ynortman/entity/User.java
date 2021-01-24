package com.knu.ynortman.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	private String email;
	private String surname;
	private String name;
	private String patronymic;
	private Date birthday;
	private long phone;
	@ManyToOne
	private Role role;
	private float fee;
	private String password;
}
