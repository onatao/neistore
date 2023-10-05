package com.neidev.store.domain.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.neidev.store.domain.user.json.buyer.BuyerResponse;
import com.neidev.store.domain.user.json.buyer.BuyerUpdateForm;
import com.neidev.store.domain.user.repository.BuyerRepository;
import com.neidev.store.handler.exceptions.BadRequestException;
import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neidev.store.domain.user.entity.Buyer;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository repository;
	
	@Transactional
	public BuyerResponse registerANewBuyer(Buyer data) {
		try {
			repository.findByEmail(data.getEmail()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("Email already registered!" + data.getEmail());
					}
			);
			repository.findByCpf(data.getCpf()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("CPF already registered!" + data.getCpf());
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
			List<BuyerResponse> response =  repository.findAll().stream()
							.map(Buyer::toResponse)
							.collect(Collectors.toList());

			if (response.isEmpty())
				throw new ResourceNotFoundException("Cannot get all registered buyers");
			return response;
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public BuyerResponse findBuyerById(String id) {
		try {
			Optional<Buyer> optionalBuyer = repository.findById(id);

			if (!optionalBuyer.isPresent())
					throw new ResourceNotFoundException("Buyer not found" + id);

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
				throw new ResourceNotFoundException("Buyer not found" + email);

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
	public void deleteBuyerById(String id) {
		try {
			repository.findById(id).orElseThrow(() ->
					new ResourceNotFoundException("Buyer doesn't exist!" + id)
			);

			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public BuyerResponse updateRegisteredBuyer(BuyerUpdateForm data, String id) {
		try {

			Optional<Buyer> optionalBuyer = repository.findById(id);
			if (!optionalBuyer.isPresent())
					throw new ResourceNotFoundException("Buyer not found" + id);

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
