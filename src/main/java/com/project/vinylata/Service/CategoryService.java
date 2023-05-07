package com.project.vinylata.Service;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.DTO.ProductByDto;
import com.project.vinylata.DTO.SpecificCategoryDto;
import com.project.vinylata.Model.Category;
import com.project.vinylata.Model.Product;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(saveCategory, CategoryDto.class);
    }

    public List<CategoryDto> show() {
        List<Category> getList = categoryRepository.findAll();
        List<CategoryDto> dtoAll = getList.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return dtoAll;
    }

    public SpecificCategoryDto showById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);

        List<Product> getList = productRepository.findByCategoryId(categoryId);
        List<ProductByDto> dtoAll = getList.stream()
                .map(entity -> this.modelMapper.map(entity, ProductByDto.class))
                .collect(Collectors.toList());

        return new SpecificCategoryDto(category.getId(),category.getCategoryName(), category.getCategoryImage(), category.getCategoryImage(), category.getCategoryDescription(), dtoAll);
    }

    public CategoryDto update(CategoryDto newCategory, Long id) {
        Category oldCategory = this.categoryRepository.findById(id);
        oldCategory.setCategoryName(newCategory.getCategoryName());
        oldCategory.setCategoryImage(newCategory.getCategoryImage());
        oldCategory.setCategoryBackground(newCategory.getCategoryBackground());
        oldCategory.setCategoryDescription(newCategory.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(oldCategory);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    public void delete(Long id) {
        Category category = this.categoryRepository.findById(id);
        this.categoryRepository.delete(category);
    }
}
