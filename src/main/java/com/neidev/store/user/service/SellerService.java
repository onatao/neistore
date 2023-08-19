package com.neidev.store.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.user.entity.Seller;
import com.neidev.store.user.repository.SellerRepository;

@Service
public class SellerService {

	@Autowired
	private SellerRepository repository;
	
	public Seller create(Seller data) {
		return repository.save(data);
	}
	
}
