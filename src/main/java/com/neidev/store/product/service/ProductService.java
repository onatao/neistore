package com.neidev.store.product.service;

import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.product.entity.Product;
import com.neidev.store.handler.exceptions.ProdutAlreadyRegisteredException;
import com.neidev.store.product.json.ProductResponse;
import com.neidev.store.product.json.ProductUpdateForm;
import com.neidev.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public ProductResponse create(Product data) {
        try {
            Optional<Product> optionalProduct =
                            repository.findByProductName(data.getProductName());

            if (optionalProduct.isPresent())
                    throw new ProdutAlreadyRegisteredException("Product already registered.");

            var upperCasedProductName = data.getProductName().toUpperCase();
            data.setProductName(upperCasedProductName);

            return repository
                    .save(data)
                        .toResponse();
        } catch (ProdutAlreadyRegisteredException e) {
            throw new ProdutAlreadyRegisteredException(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        try {
            Optional<Product> optionalProduct = repository.findById(id);

            if (!optionalProduct.isPresent())
                    throw new ResourceNotFoundException("Product doesnt exist!");

            repository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        try {
            List<ProductResponse> responseList =
                    repository.findAll().stream().map(Product::toResponse).collect(Collectors.toList());

            if (responseList.isEmpty())
                throw new ResourceNotFoundException("Cannot retrieve product list");

            return responseList;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(UUID id) {
        try {
            Optional<Product> optionalProduct = repository.findById(id);

            if (!optionalProduct.isPresent())
                    throw new ResourceNotFoundException("Product not registered");

            return optionalProduct
                    .get()
                        .toResponse();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public ProductResponse updateById(ProductUpdateForm data, UUID id) {
        try {
            Optional<Product> optionalProduct = repository.findById(id);

            if (!optionalProduct.isPresent())
                    throw new ResourceNotFoundException("Product not registered");

            var entity = optionalProduct.get();
            entity.setProductName(data.getProductName());
            entity.setDescription(data.getDescription());
            entity.setShortDescription(data.getShortDescription());
            entity.setImgUrl(data.getImgUrl());
            entity.setPrice(data.getPrice());

            return repository
                    .save(entity)
                    .toResponse();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
