package com.neidev.store.controller;

import com.neidev.store.domain.core.product.model.Product;
import com.neidev.store.domain.core.product.json.ProductResponse;
import com.neidev.store.domain.core.product.json.ProductUpdateForm;
import com.neidev.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") String id) {
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
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.findById(id)
                );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductResponse> updateRegisteredProduct(
            @RequestBody @Valid ProductUpdateForm data, String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        service.updateById(data, id)
                );
    }
}
