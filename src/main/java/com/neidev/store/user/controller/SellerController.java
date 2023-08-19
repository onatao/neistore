package com.neidev.store.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neidev.store.user.entity.Seller;
import com.neidev.store.user.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService service;
	
	@PostMapping("/create")
	public ResponseEntity<Seller> registerANewSeller(@RequestBody Seller data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
	}
}
