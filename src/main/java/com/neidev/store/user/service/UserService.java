package com.neidev.store.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.user.entity.User;
import com.neidev.store.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User create(User data) {
		return repository.save(data);
	}
}
