package com.neidev.store.user.entity;

import java.util.Objects;
import java.util.UUID;

import com.neidev.store.user.json.buyer.BuyerResponse;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_BUYER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer extends User {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String cpf;

	public BuyerResponse toResponse() {
		return BuyerResponse.builder()
				.cpf(getCpf())
				.id(getId())
				.name(getName())
				.lastName(getLastName())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.email(getEmail())
				.password(getPassword())
				.build();
	}

}
