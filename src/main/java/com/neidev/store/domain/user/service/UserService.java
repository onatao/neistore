package com.neidev.store.domain.user.service;

import com.neidev.store.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.domain.user.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User create(User data) {
		return repository.save(data);
	}
}
