package com.neidev.store.domain.repository;

import com.neidev.store.domain.core.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select p from PRODUCT p where productName=:productName")
    Optional<Product> findByProductName(String productName);

}
