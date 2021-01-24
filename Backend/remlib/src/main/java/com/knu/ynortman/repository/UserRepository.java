package com.knu.ynortman.repository;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
