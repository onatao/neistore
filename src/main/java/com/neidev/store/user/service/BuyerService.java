package com.neidev.store.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.neidev.store.handler.exceptions.BadRequestException;
import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.user.json.buyer.BuyerForm;
import com.neidev.store.user.json.buyer.BuyerResponse;
import com.neidev.store.user.json.buyer.BuyerUpdateForm;
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
						throw new CredentialAlreadyInUseException("Email already registered!");
					}
			);

			repository.findByCpf(data.getCpf()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("CPF already registered!");
					}
			);

			repository.save(data);
			return data.toResponse();
		} catch (CredentialAlreadyInUseException e) {
			throw new CredentialAlreadyInUseException(e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException("Buyer registration failed.");
		}
	}

	@Transactional(readOnly = true)
	public List<BuyerResponse> findAllBuyers() {
		try {
			return repository.findAll().stream()
					.map(Buyer::toResponse)
					.collect(Collectors.toList());

		} catch (Exception e) {
			throw new ResourceNotFoundException("Cannot find all buyers");
		}
	}

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerById(UUID id) {
		try {
			Optional<Buyer> optionalBuyer = repository.findById(id);

			if (!optionalBuyer.isPresent())
					throw new ResourceNotFoundException("Buyer not found. ID");

			return optionalBuyer.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
    }

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerByEmail(String email) {
		try {
			Optional<Buyer> optionalBuyer = repository.findByEmail(email);

			if (!optionalBuyer.isPresent())
				throw new ResourceNotFoundException("Buyer not found. EMAIL");

			return optionalBuyer.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerByCpf(String cpf) {
		try {
			Optional<Buyer> optionalBuyer = repository.findByCpf(cpf);

			if (!optionalBuyer.isPresent())
				throw new ResourceNotFoundException("Buyer not found. CPF");

			return optionalBuyer.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public void deleteBuyerById(UUID id) {
		try {
			repository.findById(id).orElseThrow(() ->
					new ResourceNotFoundException("Buyer doesn't exist!")
			);

			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public BuyerResponse updateRegisteredBuyer(BuyerUpdateForm data, UUID id) {
		try {

			Optional<Buyer> optionalBuyer = repository.findById(id);
			if (!optionalBuyer.isPresent())
					throw new ResourceNotFoundException("Buyer not found");

			var entity = optionalBuyer.get();

			entity.setEmail(data.getEmail());
			entity.setPassword(data.getPassword());
			entity.setAddress(data.getAddress());
			entity.setPhoneNumber(data.getPhoneNumber());

			return repository
					.save(entity)
						.toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
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
