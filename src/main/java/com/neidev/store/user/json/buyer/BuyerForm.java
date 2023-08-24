package com.neidev.store.user.json.buyer;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.json.user.UserForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerForm extends UserForm {

    private String cpf;

    public Buyer toEntity() {
        Buyer entity = new Buyer();

        entity.setCpf(getCpf());
        entity.setId(getId());
        entity.setName(getName());
        entity.setLastName(getLastName());
        entity.setPhoneNumber(getPhoneNumber());
        entity.setAddress(getAddress());
        entity.setEmail(getEmail());
        entity.setPassword(getPassword());

        return entity;
    }
}
