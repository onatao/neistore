package com.neidev.store.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neidev.store.user.entity.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, UUID> {

    Optional<Buyer> findByEmail(String email);

    @Query("select b from BUYER b where cpf=:cpf")
    Optional<Buyer> findByCpf(@Param("cpf") String cpf);
}
