package com.neidev.store.user.json.seller;

import com.neidev.store.user.entity.Seller;
import com.neidev.store.user.json.user.UserForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerForm extends UserForm {

   private String cnpj;

   public Seller toEntity() {
       Seller entity = new Seller();

       entity.setId(getId());
       entity.setName(getName());
       entity.setLastName(getLastName());
       entity.setAddress(getAddress());
       entity.setEmail(getEmail());
       entity.setPhoneNumber(getPhoneNumber());
       entity.setPassword(getPassword());
       entity.setCnpj(getCnpj());
       return entity;
   }

}
