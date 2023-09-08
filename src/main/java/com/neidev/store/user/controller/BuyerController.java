package com.neidev.store.user.controller;

import com.neidev.store.user.json.buyer.BuyerResponse;
import com.neidev.store.user.json.buyer.BuyerUpdateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.service.BuyerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService service;
	
	@PostMapping("/register")
	public ResponseEntity<BuyerResponse> registerANewBuyer(@RequestBody @Valid Buyer data) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(
						service.registerANewBuyer(data)
				);
	}

	@GetMapping("/all")
	public ResponseEntity<List<BuyerResponse>> getAllRegisteredBuyers() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findAllBuyers()
				);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<BuyerResponse> getBuyerById(@PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findBuyerById(id)
				);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<BuyerResponse> getBuyerByEmail(@PathVariable String email) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
					service.findBuyerByEmail(email)
				);
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<BuyerResponse> getBuyerByCpf(@PathVariable String cpf) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findBuyerByCpf(cpf)
				);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<BuyerResponse> updateRegisteredBuyer(@RequestBody @Valid BuyerUpdateForm data, @PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.updateRegisteredBuyer(data, id)
				);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteRegisteredBuyerById(@PathVariable UUID id) {
		service.deleteBuyerById(id);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT).build();
	}
}
