package com.neidev.store.product.controller;

import com.neidev.store.product.entity.Product;
import com.neidev.store.product.json.ProductResponse;
import com.neidev.store.product.json.ProductUpdateForm;
import com.neidev.store.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/register")
    public ResponseEntity<ProductResponse> registerNewProduct(@RequestBody @Valid Product data) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.create(data)
                );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<ProductResponse>> getAllRegisteredProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.findAll()
                );
    }

    @GetMapping("p/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.findById(id)
                );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductResponse> updateRegisteredProduct(
            @RequestBody @Valid ProductUpdateForm data, UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.updateById(data, id)
                );
    }
}
