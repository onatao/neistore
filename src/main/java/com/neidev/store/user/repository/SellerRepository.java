package com.neidev.store.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.neidev.store.user.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neidev.store.user.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String>{

    Optional<Seller> findByEmail(String email);

    @Query("select s from SELLER s where cnpj=:cnpj")
    Optional<Seller> findByCnpj(@Param("cnpj") String cnpj);

}
