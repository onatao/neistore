package com.neidev.store.domain.core.product.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    @NotBlank
    private String id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String productName;
    @Size(min = 20, max = 50)
    private String shortDescription;
    @NotBlank
    private BigDecimal price;
}
