package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Setter
@lombok.Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificCategoryDto {
    private Long id;
    private String categoryName;
    private String categoryImage;
    private String categoryBackground;
    private String categoryDescription;
    private List<ProductByCateDto> products;
}
