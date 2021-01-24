package com.knu.ynortman.service;

import com.knu.ynortman.dto.GetUserDTO;
import com.knu.ynortman.dto.PostUserDTO;

public interface UserService {
	Iterable<GetUserDTO> getAllUsers();
	GetUserDTO getUser(String id);
	GetUserDTO addUser(PostUserDTO user);
	GetUserDTO updateUser(PostUserDTO user, String id);
	void deleteUser(String id);
}
