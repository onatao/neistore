package com.neidev.store.user.entity;

import com.neidev.store.user.json.buyer.BuyerResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "BUYER")
@Table(name = "TB_BUYER")
@Getter
@Setter
@NoArgsConstructor
public class Buyer extends User {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String cpf;

	public Buyer(String id, String name, String lastName, String phoneNumber,
				 String address, String email, String password, String cpf) {
		super(id, name, lastName, phoneNumber, address, email, password);
		this.cpf = cpf;

	}

	public BuyerResponse toResponse() {
		return BuyerResponse.builder()
				.cpf(getCpf())
				.id(getId())
				.name(getName())
				//.lastName(getLastName())
				.phoneNumber(getPhoneNumber())
				//.address(getAddress())
				//.email(getEmail())
				.password(getPassword())
				.build();
	}

}
