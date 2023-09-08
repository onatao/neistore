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
	
	@GetMapping("/register")
	public ResponseEntity<SellerResponse> registerANewSeller(@RequestBody @Valid Seller data) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.registerANewSeller(data)
				);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<SellerResponse> deleteRegisteredSellerById(@PathVariable UUID id) {
		service.deleteSellerById(id);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@GetMapping("/all")
	public ResponseEntity<List<SellerResponse>> getAllRegisteredSellers() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findAllSellers()
				);
	}

	@GetMapping("id/{id}")
	public ResponseEntity<SellerResponse> getSellerById(@PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerById(id)
				);
	}

	@GetMapping("cnpj/{cnpj}")
	public ResponseEntity<SellerResponse> getSellerByCnpj(@PathVariable String cnpj) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerByCnpj(cnpj)
				);
	}

	@GetMapping("email/{email}")
	public ResponseEntity<SellerResponse> getSellerByEmail(@PathVariable String email) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findSellerByEmail(email)
				);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<SellerResponse> updateRegisteredSeller(@RequestBody @Valid SellerUpdateForm data,
																 @PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.updateRegisteredSeller(data, id)
				);
	}


}
