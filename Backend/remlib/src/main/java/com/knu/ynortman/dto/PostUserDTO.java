package com.knu.ynortman.dto;

import java.util.Date;

import com.knu.ynortman.entity.Role;
import com.knu.ynortman.entity.User;

import lombok.Data;

@Data
public class PostUserDTO {
	private String email;
	private String surname;
	private String name;
	private String patronymic;
	private Date birthday;
	private long phone;
	private Role role;
	private float fee;
	private String password;
	
	public static PostUserDTO toDTO(User user) {
		PostUserDTO userDTO = new PostUserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setSurname(user.getSurname());
		userDTO.setName(user.getName());
		userDTO.setPatronymic(user.getPatronymic());
		userDTO.setBirthday(user.getBirthday());
		userDTO.setPhone(user.getPhone());
		userDTO.setRole(user.getRole());
		userDTO.setFee(user.getFee());
		userDTO.setPassword(user.getPassword());
		return userDTO;
	}
	
	public static User fromDTO(PostUserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setSurname(userDTO.getSurname());
		user.setName(userDTO.getName());
		user.setPatronymic(userDTO.getPatronymic());
		user.setBirthday(userDTO.getBirthday());
		user.setPhone(userDTO.getPhone());
		user.setRole(userDTO.getRole());
		user.setFee(userDTO.getFee());
		user.setPassword(userDTO.getPassword());
		return user;
	}
}
