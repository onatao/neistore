package com.neidev.store.user.service;

import com.neidev.store.user.handler.exceptions.BadRequestException;
import com.neidev.store.user.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.user.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.user.json.seller.SellerResponse;
import com.neidev.store.user.json.seller.SellerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neidev.store.user.entity.Seller;
import com.neidev.store.user.repository.SellerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SellerService {

	@Autowired
	private SellerRepository repository;

	@Transactional
	public SellerResponse registerANewSeller(Seller data) {
		try {
			repository.findByEmail(data.getEmail()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("Email already registered!");
					}
			);

			repository.findByCnpj(data.getCnpj()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("CNPJ already registered!");
					}
			);

			return repository
					.save(data)
						.toResponse();
		} catch (CredentialAlreadyInUseException e) {
			throw new CredentialAlreadyInUseException(e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException("Seller registration failed.");
		}
	}

	@Transactional(readOnly = true)
	public List<SellerResponse> findAllSellers() {
		try {
			return repository.findAll()
					.stream()
					.map(Seller::toResponse)
					.collect(Collectors.toList());

		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Cannot find all sellers.");
		}
	}

	@Transactional(readOnly = true)
	public SellerResponse findSellerById(UUID id) {
		try {
			Optional<Seller> optionalSeller = repository.findById(id);
			if (!optionalSeller.isPresent())
				throw new ResourceNotFoundException("Seller not found.");

			return optionalSeller.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public SellerResponse findSellerByEmail(String email) {
		try {
			Optional<Seller> optionalSeller = repository.findByEmail(email);
			if (!optionalSeller.isPresent())
					throw new ResourceNotFoundException("Seller not registered.");

			return optionalSeller.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public SellerResponse findSellerByCnpj(String cnpj) {
		try {
			Optional<Seller> optionalSeller = repository.findByCnpj(cnpj);
			if (!optionalSeller.isPresent())
					throw new ResourceNotFoundException("Seller not registered.");

			return optionalSeller.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public void deleteSellerById(UUID id) {
		try {
			repository.findById(id).orElseThrow(
					() -> {
						throw new ResourceNotFoundException("Seller not registered.");
					}
			);

			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public SellerResponse updateRegisteredSeller(SellerUpdateForm data, UUID id) {
		try {
			Optional<Seller> optionalSeller = repository.findById(id);
			if (!optionalSeller.isPresent())
					throw new ResourceNotFoundException("Seller not registered.");

			var entity = optionalSeller.get();
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


	public

	boolean isEmailInUse(String email) {
		try {
			Optional<Seller> optionalSeller = repository.findByEmail(email);
			if (!optionalSeller.isPresent()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	boolean isCnpjInUse(String cnpj) {
		try {
			Optional<Seller> optionalBuyer = repository.findByEmail(cnpj);
			if (optionalBuyer.isPresent()) return true;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
