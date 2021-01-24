package com.knu.ynortman.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.knu.ynortman.dto.GetUserDTO;
import com.knu.ynortman.dto.PostUserDTO;
import com.knu.ynortman.exception.ApiError;
import com.knu.ynortman.exception.ServerException;
import com.knu.ynortman.service.UserService;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> userList() {
		try {
			List<GetUserDTO> users = (List<GetUserDTO>) userService.getAllUsers();
			if (users != null && users.size() != 0) {
				return new ResponseEntity<Iterable<GetUserDTO>>(users, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no user found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> userById(@PathVariable("id") String id) {
		try {
			GetUserDTO user =  userService.getUser(id);
			if (user != null) {
				return new ResponseEntity<GetUserDTO>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, "There were no user found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postUser(@RequestBody PostUserDTO user) {
		try {
			return new ResponseEntity<GetUserDTO>(userService.addUser(user), HttpStatus.CREATED);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putUser(@RequestBody PostUserDTO user, @PathVariable String id) {
		try {
			return new ResponseEntity<GetUserDTO>(userService.updateUser(user, id), HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteAlgo(@PathVariable("id") String id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<GetUserDTO>(HttpStatus.OK);
		} catch (ServerException e) {
			return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
