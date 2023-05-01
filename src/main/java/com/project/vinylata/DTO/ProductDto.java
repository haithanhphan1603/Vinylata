package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String productTitle;
    private String productAttributes;
    private String productImage;
    private boolean productStatus;
    private Double productPricing;
    private String productDescription;
    private String productDetail;
    private CategoryDto category;
    private VendorDto vendor;
}
