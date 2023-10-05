package com.neidev.store.domain.user.json.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

    @NotBlank
    private String id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String name;
    @NotBlank
    @Size(min = 5, max = 20)
    private String lastName;
    @NotBlank
    @Size(min = 13, max =13)
    private String phoneNumber;
    @NotBlank
    @Size(min = 10, max = 50)
    private String address;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;

}
