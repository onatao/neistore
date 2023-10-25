package com.neidev.store.domain.core.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.neidev.store.security.enums.AuthRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public sealed class User implements UserDetails permits Buyer, Seller {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String id;
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
	private AuthRole role;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == AuthRole.ADMIN) {
			return List.of(
					new SimpleGrantedAuthority("admin"),
					new SimpleGrantedAuthority("seller"),
					new SimpleGrantedAuthority("buyer")

			);
		}
		return List.of(new SimpleGrantedAuthority(this.role.getDescription()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
