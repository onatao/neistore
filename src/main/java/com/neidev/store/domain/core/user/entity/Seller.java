package com.neidev.store.domain.core.user.entity;

import com.neidev.store.domain.core.user.json.seller.SellerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "SELLER")
@Table(name = "TB_SELLER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true, length = 14)
	private String cnpj;

	public Seller(String id, String name, String lastName, String phoneNumber,
				   String address, String email, String password, String cnpj) {
		super(id, name, lastName, phoneNumber, address, email, password);
		this.cnpj = cnpj;
	}

	public SellerResponse toResponse() {
		return SellerResponse.builder()
				.id(getId())
				.name(getName())
				.lastName(getLastName())
				.email(getEmail())
				.address(getAddress())
				.password(getPassword())
				.cnpj(getCnpj())
				.phoneNumber(getPhoneNumber())
				.build();
	}
}
