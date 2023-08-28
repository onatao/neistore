package com.neidev.store.user.entity;

import java.util.Objects;
import java.util.UUID;

import com.neidev.store.user.json.seller.SellerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	public SellerResponse toResponse() {
		return SellerResponse.builder()
				.id(getId())
				.name(getName())
				.lastName(getLastName())
				.email(getEmail())
				.address(getAddress())
				.password(getPassword())
				.cnpj(getCnpj())
				.build();
	}
}
