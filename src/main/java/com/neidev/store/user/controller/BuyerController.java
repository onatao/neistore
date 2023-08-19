package com.neidev.store.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.service.BuyerService;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService service;
	
	@PostMapping(value = "/create")
	public ResponseEntity<Buyer> registerANewBuyer(@RequestBody Buyer data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
	}
}
