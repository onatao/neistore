package com.neidev.store.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository repository;
	
	public Buyer create(Buyer data) {
		return repository.save(data);
	}
}
