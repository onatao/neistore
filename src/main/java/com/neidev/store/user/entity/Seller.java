package com.neidev.store.user.entity;

import java.util.Objects;
import java.util.UUID;

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

@Entity
@Table(name = "TB_SELLER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true, length = 14)
	private String cnpj;
}
