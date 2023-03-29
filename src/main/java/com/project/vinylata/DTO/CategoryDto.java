package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Setter
@lombok.Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String categoryName;
    private String categoryImage;
    private String categoryDescription;
}
