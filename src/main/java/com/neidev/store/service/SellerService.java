package com.neidev.store.service;

import com.neidev.store.domain.core.user.model.Seller;
import com.neidev.store.domain.core.user.json.seller.SellerResponse;
import com.neidev.store.domain.core.user.json.seller.SellerUpdateForm;
import com.neidev.store.domain.repository.SellerRepository;
import com.neidev.store.domain.handler.exceptions.BadRequestException;
import com.neidev.store.domain.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.domain.handler.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
						throw new CredentialAlreadyInUseException("Email already registered! " + data.getEmail());
					}
			);

			repository.findByCnpj(data.getCnpj()).ifPresent(
					user -> {
						throw new CredentialAlreadyInUseException("CNPJ already registered! " + data.getCnpj());
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
			List<SellerResponse> responseList = repository.findAll().stream()
							.map(Seller::toResponse)
							.collect(Collectors.toList());

			if (responseList.isEmpty())
				throw new ResourceNotFoundException("Cannot get all registered sellers");

			return responseList;
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public SellerResponse findSellerById(String id) {
		try {
			Optional<Seller> optionalSeller = repository.findById(id);
			if (!optionalSeller.isPresent())
				throw new ResourceNotFoundException("Seller not found." + id);

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
					throw new ResourceNotFoundException("Cannot find seller by email: " + email);

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
					throw new ResourceNotFoundException("Cannot find seller by CNPJ: " + cnpj);

			return optionalSeller.get().toResponse();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public void deleteSellerById(String id) {
		try {
			repository.findById(id).orElseThrow(
					() -> {
						throw new ResourceNotFoundException("Seller doesn't exist!" + id);
					}
			);
			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public SellerResponse updateRegisteredSeller(SellerUpdateForm data, String id) {
		try {
			Optional<Seller> optionalSeller = repository.findById(id);
			if (!optionalSeller.isPresent())
					throw new ResourceNotFoundException("Seller not registered: " + id);

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
