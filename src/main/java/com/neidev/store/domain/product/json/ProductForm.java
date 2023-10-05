package com.neidev.store.domain.product.json;

import com.neidev.store.domain.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    @NotBlank
    private String id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String productName;
    @NotBlank
    @Size(min = 30, max = 200)
    private String description;
    @Size(min = 20, max = 50)
    private String shortDescription;
    @NotBlank
    private String imgUrl;
    @NotBlank
    private BigDecimal price;

    public Product toEntity() {
        Product entity = new Product();

        entity.setId(getId());
        entity.setProductName(getProductName());
        entity.setDescription(getDescription());
        entity.setShortDescription(getShortDescription());
        entity.setImgUrl(getImgUrl());
        entity.setPrice(getPrice());

        return entity;
    }
}
