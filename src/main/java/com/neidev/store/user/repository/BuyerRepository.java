package com.neidev.store.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neidev.store.user.entity.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, UUID>{

}
