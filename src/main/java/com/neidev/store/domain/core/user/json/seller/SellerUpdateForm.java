package com.neidev.store.domain.core.user.json.seller;


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
public class SellerUpdateForm {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 10)
    private String password;
    @NotBlank
    @Size(min = 13, max = 13)
    private String phoneNumber;
    @NotBlank
    @Size(min = 10, max = 50)
    private String address;

    public SellerForm toForm() {
        SellerForm form = new SellerForm();
        form.setEmail(getEmail());
        form.setPassword(getPassword());
        form.setAddress(getAddress());
        form.setPhoneNumber(getPhoneNumber());
        return form;
    }
}
