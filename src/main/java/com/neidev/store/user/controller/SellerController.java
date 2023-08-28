package com.neidev.store.user.controller;

import com.neidev.store.user.json.seller.SellerResponse;
import com.neidev.store.user.json.seller.SellerUpdateForm;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neidev.store.user.entity.Seller;
import com.neidev.store.user.service.SellerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService service;
	
	@GetMapping
	public ResponseEntity<SellerResponse> registerANewSeller(@RequestBody @Valid Seller data) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.registerANewSeller(data)
				);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SellerResponse> deleteRegisteredSellerById(@PathVariable UUID id) {
		service.deleteSellerById(id);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@GetMapping
	public ResponseEntity<List<SellerResponse>> findAllRegisteredSellers() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findAllSellers()
				);
	}

	@GetMapping("id/{id}")
	public ResponseEntity<SellerResponse> findSellerById(@PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerById(id)
				);
	}

	@GetMapping("cnpj/{cnpj}")
	public ResponseEntity<SellerResponse> findSellerByCnpj(@PathVariable String cnpj) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerByCnpj(cnpj)
				);
	}

	@GetMapping("email/{email}")
	public ResponseEntity<SellerResponse> findSellerByEmail(@PathVariable String email) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerByEmail(email)
				);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SellerResponse> updateRegisteredSeller(@RequestBody @Valid SellerUpdateForm data,
																 @PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.updateRegisteredSeller(data, id)
				);
	}


}
