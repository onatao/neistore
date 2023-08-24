package com.neidev.store.user.controller;

import com.neidev.store.user.json.buyer.BuyerResponse;
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
	
	@PostMapping
	public ResponseEntity<BuyerResponse> registerANewBuyer(@RequestBody Buyer data) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(
						service.registerANewBuyer(data)
				);
	}

	@GetMapping
	public ResponseEntity<List<BuyerResponse>> findAllBuyers() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findAllBuyers()
				);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BuyerResponse> findBuyerById(@PathVariable UUID id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.findBuyerById(id)
				);
	}

	@PutMapping
	public ResponseEntity<BuyerResponse> updateRegisteredBuyer(@RequestBody Buyer data) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
						service.updateRegisteredBuyer(data)
				);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteBuyerById(@PathVariable UUID id) {
		service.deleteBuyerById(id);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT).build();
	}
}
