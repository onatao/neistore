package com.neidev.store.product.controller;

import com.neidev.store.product.entity.Product;
import com.neidev.store.product.json.ProductResponse;
import com.neidev.store.product.repository.ProductRepository;
import com.neidev.store.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> registerANewProduct(@RequestBody @Valid Product data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.create(data)
                );
    }
}
