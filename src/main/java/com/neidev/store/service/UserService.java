package com.neidev.store.service;

import com.neidev.store.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.domain.core.user.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User create(User data) {
		return repository.save(data);
	}
}
