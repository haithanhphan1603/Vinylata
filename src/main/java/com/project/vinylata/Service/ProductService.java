package com.project.vinylata.Service;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.DTO.ProductDto;
import com.project.vinylata.DTO.ProductResponse;
import com.project.vinylata.Model.Category;
import com.project.vinylata.Model.Product;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    //CREATE PRODUCT
    public ProductDto create(ProductDto productDto, Long categoryId){
        Category category = this.categoryRepository.findById(categoryId);
        //Dto to entity
        Product product = toEntity(productDto);
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        //Entity to Dto
        ProductDto dto = toDto(saveProduct);
        return dto;
    }

    //READ PRODUCT
    public ProductResponse show(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if (sortDir.trim().toLowerCase().equals("asc")){
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        List<Product> productPage = page.getContent();
//        List<Product> productStatus = productPage.stream().filter(product -> product.isProductStatus()).collect(Collectors.toList());
        List<ProductDto> dtoAll = productPage.stream().map(product -> this.toDto(product)).collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setContent(dtoAll);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        //Dto to entity
//        List<Product> getList = productRepository.findAll();
        //Entity to Dto
//        List<ProductDto> dtoAll = getList.stream()
//                                             .map(product -> this.toDto(product))
//                                             .collect(Collectors.toList());
        return response;
    }

    //READ 1 PRODUCT
    public ProductDto showById(Long id){
        Product productById = productRepository.findById(id);
        ProductDto dtoById = this.toDto(productById);
        return dtoById;
    }

    //UPDATE PRODUCT
    public ProductDto update(Long id, ProductDto newProduct){
        Product oldProduct = productRepository.findById(id);
        oldProduct.setProductTitle(newProduct.getProductTitle());
        oldProduct.setProductAttributes(newProduct.getProductAttributes());
        oldProduct.setProductImage(newProduct.getProductImage());
        oldProduct.setProductStatus(newProduct.isProductStatus());
        oldProduct.setProductPricing(newProduct.getProductPricing());
        oldProduct.setProductDescription(newProduct.getProductDescription());
        oldProduct.setProductDetail(newProduct.getProductDetail());
        oldProduct.setProductSlug(newProduct.getProductSlug());
        Product updatedProduct = productRepository.save(oldProduct);
        ProductDto dtoUpdate = toDto(updatedProduct);
        return dtoUpdate;
    }

    //DELETE PRODUCT
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    // DTO to Entity
    public Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductTitle(productDto.getProductTitle());
        product.setProductAttributes(productDto.getProductAttributes());
        product.setProductImage(productDto.getProductImage());
        product.setProductStatus(productDto.isProductStatus());
        product.setProductPricing(productDto.getProductPricing());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductDetail(productDto.getProductDetail());
        product.setProductSlug(productDto.getProductSlug());

        return product;
    }

    //Entity to DTO
    public ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductTitle(product.getProductTitle());
        productDto.setProductAttributes(product.getProductAttributes());
        productDto.setProductImage(product.getProductImage());
        productDto.setProductStatus(product.isProductStatus());
        productDto.setProductPricing(product.getProductPricing());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductDetail(product.getProductDetail());
        productDto.setProductSlug(product.getProductSlug());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setCategoryName(product.getCategory().getCategoryName());
        categoryDto.setCategoryImage(product.getProductImage());
        categoryDto.setCategoryDescription(product.getCategory().getCategoryDescription());

        productDto.setCategory(categoryDto);

        return productDto;
    }
}
