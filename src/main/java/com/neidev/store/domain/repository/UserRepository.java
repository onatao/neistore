package com.neidev.store.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.neidev.store.domain.core.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByEmail(String email);
}
