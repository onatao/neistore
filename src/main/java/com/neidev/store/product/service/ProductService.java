package com.neidev.store.product.service;

import com.neidev.store.product.entity.Product;
import com.neidev.store.product.handler.exception.ProdutAlreadyRegisteredException;
import com.neidev.store.product.json.ProductResponse;
import com.neidev.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public ProductResponse create(Product data) {
        try {
            Optional<Product> optionalProduct =
                            repository.findById(data.getId());

            if (optionalProduct.isPresent())
                    throw new ProdutAlreadyRegisteredException("Product already registered.");

            return repository
                    .save(data)
                        .toResponse();
        } catch (ProdutAlreadyRegisteredException e) {
            throw new ProdutAlreadyRegisteredException(e.getMessage());
        }
    }
}
