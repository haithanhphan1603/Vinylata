package com.project.vinylata.Service;

import com.github.slugify.Slugify;
import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.DTO.ProductDto;
import com.project.vinylata.DTO.ProductResponse;
import com.project.vinylata.DTO.VendorDto;
import com.project.vinylata.Model.Category;
import com.project.vinylata.Model.Product;
import com.project.vinylata.Model.Vendor;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Repository.ProductRepository;
import com.project.vinylata.Repository.VendorRepository;
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
    final Slugify slg = Slugify.builder().build();
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ModelMapper modelMapper;

    //CREATE PRODUCT
    public ProductDto create(ProductDto productDto, Long categoryId, Long vendorId) {
        Category category = this.categoryRepository.findById(categoryId);
        Vendor vendor = this.vendorRepository.findById(vendorId);
        //Dto to entity
        Product product = toEntity(productDto);
        product.setCategory(category);
        product.setVendor(vendor);
        Product saveProduct = productRepository.save(product);
        //Entity to Dto
        ProductDto dto = toDto(saveProduct);
        return dto;
    }

    //READ PRODUCT
    public ProductResponse show(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        List<Product> productPage = page.getContent();
        List<ProductDto> dtoAll = productPage.stream().map(product -> this.toDto(product)).collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setContent(dtoAll);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    //READ 1 PRODUCT
    public ProductDto showById(Long id) {
        Product productById = productRepository.findById(id);
        ProductDto dtoById = this.toDto(productById);
        return dtoById;
    }

    //UPDATE PRODUCT
    public ProductDto update(Long id, ProductDto newProduct) {
        Product oldProduct = productRepository.findById(id);
        oldProduct.setProductTitle(newProduct.getProductTitle());
        oldProduct.setProductAttributes(newProduct.getProductAttributes());
        oldProduct.setProductImage(newProduct.getProductImage());
        oldProduct.setProductStatus(newProduct.isProductStatus());
        oldProduct.setProductPricing(newProduct.getProductPricing());
        oldProduct.setProductDescription(newProduct.getProductDescription());
        oldProduct.setProductDetail(newProduct.getProductDetail());

        Product updatedProduct = productRepository.save(oldProduct);
        ProductDto dtoUpdate = toDto(updatedProduct);
        return dtoUpdate;
    }

    //DELETE PRODUCT
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    // DTO to Entity
    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductTitle(productDto.getProductTitle());
        product.setProductAttributes(productDto.getProductAttributes());
        product.setProductImage(productDto.getProductImage());
        product.setProductStatus(productDto.isProductStatus());
        product.setProductPricing(productDto.getProductPricing());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductDetail(productDto.getProductDetail());

        return product;
    }

    //Entity to DTO
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductTitle(product.getProductTitle());
        productDto.setProductAttributes(product.getProductAttributes());
        productDto.setProductImage(product.getProductImage());
        productDto.setProductStatus(product.isProductStatus());
        productDto.setProductPricing(product.getProductPricing());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductDetail(product.getProductDetail());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setCategoryName(product.getCategory().getCategoryName());
        categoryDto.setCategoryImage(product.getProductImage());
        categoryDto.setCategoryDescription(product.getCategory().getCategoryDescription());

        VendorDto vendorDto = new VendorDto();
        vendorDto.setId(product.getVendor().getId());
        vendorDto.setVendorName(product.getVendor().getVendorName());

        productDto.setCategory(categoryDto);
        productDto.setVendor(vendorDto);

        return productDto;
    }
}
