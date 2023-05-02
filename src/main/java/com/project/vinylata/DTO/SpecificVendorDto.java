package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Setter
@lombok.Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificVendorDto {
    private Long id;
    private String vendorName;
    private String vendorImage;
    private List<ProductByDto> products;
}
