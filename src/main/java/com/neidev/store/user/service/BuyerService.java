package com.neidev.store.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neidev.store.handler.exceptions.UserAlreadyRegisteredException;
import com.neidev.store.mapper.DozerMapper;
import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.repository.BuyerRepository;
import com.neidev.store.user.shared.buyer.BuyerDTO;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository repository;
	
	@Transactional
	public BuyerDTO create(Buyer data) {
		try {
			Optional<Buyer> optionalBuyer = repository.findById(data.getId());
			if (optionalBuyer.isPresent())
					throw new UserAlreadyRegisteredException("Buyer already registered!");
			
			if(optionalBuyer.get().getEmail() == data.getEmail())
					throw new UserAlreadyRegisteredException("Email already in use!");
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return DozerMapper.parseObject(data, BuyerDTO.class);
	}
}
