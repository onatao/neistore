package com.neidev.store.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neidev.store.user.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, UUID>{

}
