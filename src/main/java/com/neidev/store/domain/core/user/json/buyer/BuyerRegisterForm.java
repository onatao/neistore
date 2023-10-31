package com.neidev.store.domain.core.user.json.buyer;

import com.neidev.store.domain.core.user.model.Buyer;
import com.neidev.store.security.enums.AuthRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerRegisterForm {

    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
    private AuthRole role;
    private String cpf;

    public Buyer toEntity() {
        Buyer buyer = new Buyer();
        buyer.setId(getId());
        buyer.setName(getName());
        buyer.setLastName(getLastName());
        buyer.setAddress(getAddress());
        buyer.setEmail(getEmail());
        buyer.setPassword(getPassword());
        buyer.setRole(getRole());
        buyer.setCpf(getCpf());
        return buyer;
    }

}
