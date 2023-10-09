package com.neidev.store.domain.core.user.json.buyer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyerUpdateForm {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 10, max = 50)
    private String address;
    @NotBlank
    @Size(min = 13, max = 13)
    private String phoneNumber;

    public BuyerForm toForm() {
        BuyerForm form = new BuyerForm();
        form.setEmail(getEmail());
        form.setPassword(getPassword());
        form.setPhoneNumber(getPhoneNumber());
        form.setAddress(getAddress());
        return form;
    }
}
