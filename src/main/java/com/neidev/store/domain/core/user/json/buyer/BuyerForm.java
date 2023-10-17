package com.neidev.store.domain.core.user.json.buyer;

import com.neidev.store.domain.core.user.model.Buyer;
import com.neidev.store.domain.core.user.json.user.UserForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerForm extends UserForm {

    @NotBlank
    @Size(min = 11, max = 11)
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
