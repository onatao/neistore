package com.neidev.store.domain.product.json;

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
public class ProductUpdateForm {

    @NotBlank
    @Size(min = 5, max = 20)
    private String productName;
    @Size(min = 30, max = 200)
    private String description;
    @Size(min = 20, max = 50)
    private String shortDescription;
    @NotBlank
    private String imgUrl;
    @NotBlank
    private BigDecimal price;

    public ProductForm toForm() {
        ProductForm form = new ProductForm();
        form.setProductName(getProductName());
        form.setDescription(getDescription());
        form.setShortDescription(getShortDescription());
        form.setImgUrl(getImgUrl());
        form.setPrice(getPrice());
        return form;
    }
}
