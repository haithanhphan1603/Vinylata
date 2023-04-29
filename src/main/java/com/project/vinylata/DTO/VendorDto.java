package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VendorDto {
    private Long id;
    private String vendorName;
    private String vendorImage;
}
