package com.project.vinylata.DTO;

import com.project.vinylata.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDto> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private boolean lastPage;
}
