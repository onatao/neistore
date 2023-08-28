package com.neidev.store.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.neidev.store.handler.exceptions.BadRequestException;
import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.user.json.buyer.BuyerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository repository;
	
	@Transactional
	public BuyerResponse registerANewBuyer(Buyer data) {
		try {
			repository.findByEmail(data.getEmail()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("Email already in use!");
					}
			);

			repository.findByCpf(data.getCpf()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("CPF already in use!");
					}
			);

			repository.save(data);
			return data.toResponse();
		} catch (CredentialAlreadyInUseException e) {
			throw new CredentialAlreadyInUseException(e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException("User registration failed.");
		}
	}

	@Transactional(readOnly = true)
	public List<BuyerResponse> findAllBuyers() {
		return repository.findAll().stream()
				.map(Buyer::toResponse).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerById(UUID id) {
		Optional<Buyer> optionalBuyer = repository.findById(id);
		return optionalBuyer.get().toResponse();
	}

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerByEmail(String email) {
		Optional<Buyer> optionalBuyer = repository.findByEmail(email);
		return optionalBuyer.get().toResponse();
	}@Transactional(readOnly = true)
	public BuyerResponse findBuyerByCpf(String cpf) {
		Optional<Buyer> optionalBuyer = repository.findByEmail(cpf);
		return optionalBuyer.get().toResponse();
	}

	@Transactional
	public void deleteBuyerById(UUID id) {
		repository.deleteById(id);
	}

	@Transactional
	public BuyerResponse updateRegisteredBuyer(Buyer data) {
		Optional<Buyer> optionalBuyer = repository.findById(data.getId());
		var entity = optionalBuyer.get();

		entity.setName(data.getName());
		entity.setLastName(data.getLastName());
		entity.setCpf(data.getCpf());
		entity.setEmail(data.getEmail());
		entity.setAddress(data.getAddress());
		entity.setPassword(data.getPassword());

		return entity.toResponse();
	}

	boolean isEmailInUse(String email) {
		try {
			Optional<Buyer> optionalBuyer = repository.findByEmail(email);
			if (optionalBuyer.isPresent()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	boolean isCpfInUse(String cpf) {
		try {
			Optional<Buyer> optionalBuyer = repository.findByCpf(cpf);
			if (optionalBuyer.isPresent()) return true;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
