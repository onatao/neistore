package com.neidev.store.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neidev.store.domain.core.user.model.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, String> {

    Optional<Buyer> findByEmail(String email);

    @Query("select b from BUYER b where cpf=:cpf")
    Optional<Buyer> findByCpf(@Param("cpf") String cpf);
}
