package com.knu.ynortman.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.knu.ynortman.dto.GetUserDTO;
import com.knu.ynortman.dto.PostUserDTO;
import com.knu.ynortman.entity.User;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.repository.UserRepository;

@Service
public class UserServiceDB implements UserService {
	
	private final UserRepository userRepo;
	
	public UserServiceDB(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public Iterable<GetUserDTO> getAllUsers() {
		List<GetUserDTO> userDTO = new LinkedList<>();
		try {
			userRepo.findAll().forEach(new Consumer<User>() {
				@Override
				public void accept(User t) {
					userDTO.add(GetUserDTO.toDTO(t));
				}
			});
					
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return userDTO;
	}

	@Override
	public GetUserDTO getUser(String id) {
		Optional<User> user = null;
		try {
			user = userRepo.findById(id);
			if(user.isPresent()) {
				return GetUserDTO.toDTO(user.get());
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return null;
	}

	@Override
	public GetUserDTO addUser(PostUserDTO user) {
		try {
			User usr = PostUserDTO.fromDTO(user);
			return GetUserDTO.toDTO(userRepo.save(usr));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public GetUserDTO updateUser(PostUserDTO user, String id) {
		try {
			Optional<User> oldUser = userRepo.findById(id);
			if(oldUser.isEmpty()) {
				return null;
			} else {
				user.setEmail(id);
			}
			return GetUserDTO.toDTO(userRepo.save(PostUserDTO.fromDTO(user)));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public void deleteUser(String id) {
		try {
			userRepo.deleteById(id);
		} catch(Exception e) {
			throw new ServerException(e.getMessage());
		}
		
	}

}
