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

    public Seller toEntity(SellerRegisterForm data) {
        Seller s = new Seller();
        s.setId(data.getId());
        s.setName(data.getName());
        s.setLastName(data.getLastName());
        s.setPhoneNumber(data.getPhoneNumber());
        s.setAddress(data.getAddress());
        s.setEmail(data.getEmail());
        s.setPassword(data.getPassword());
        s.setRole(data.getRole());
        s.setCnpj(data.getCnpj());
        return s;
    }

}
