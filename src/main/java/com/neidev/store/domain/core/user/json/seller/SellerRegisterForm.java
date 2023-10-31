package com.neidev.store.domain.core.user.json.seller;

import com.neidev.store.domain.core.user.model.Seller;
import com.neidev.store.security.enums.AuthRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegisterForm {

    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
    private AuthRole role;
    private String cnpj;

    public Seller toEntity() {
        Seller s = new Seller();
        s.setId(getId());
        s.setName(getName());
        s.setLastName(getLastName());
        s.setPhoneNumber(getPhoneNumber());
        s.setAddress(getAddress());
        s.setEmail(getEmail());
        s.setPassword(getPassword());
        s.setRole(getRole());
        s.setCnpj(getCnpj());
        return s;
    }

}
