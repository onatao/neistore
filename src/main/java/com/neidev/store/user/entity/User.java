package com.neidev.store.user.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	private UUID id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false, length = 15) 
	private String phoneNumber;
	@Column(nullable = false, length = 50)
	private String address;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	

}
