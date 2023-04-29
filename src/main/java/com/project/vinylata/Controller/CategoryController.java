package com.project.vinylata.Controller;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/categories")
public class CategoryController {
    static final String SUCCESS_MESSAGE = "success";
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Object> create(@RequestBody CategoryDto category){
//        CategoryDto newCategory = this.categoryService.create(category);
//        return new ResponseEntity<CategoryDto>(newCategory, HttpStatus.CREATED);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.CREATED,this.categoryService.create(category));
    }

    @GetMapping("/")
    public ResponseEntity<Object> show(){
//        List<CategoryDto> categoryList = this.categoryService.show();
//        return new ResponseEntity<List<CategoryDto>>(categoryList, HttpStatus.OK);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.show());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> showById(@PathVariable Long categoryId){
//        CategoryDto detailCategory = this.categoryService.showById(categoryId);
//        return new ResponseEntity<CategoryDto>(detailCategory, HttpStatus.ACCEPTED);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.showById(categoryId));
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Object> update(@RequestBody CategoryDto newCategory, @PathVariable Long categoryId){
//        CategoryDto updatedCategory = this.categoryService.update(newCategory, categoryId);
//        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.ACCEPTED);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.update(newCategory, categoryId));
    }

    @DeleteMapping("/del/{categoryId}")
    public ResponseEntity<Object> delete(@PathVariable Long categoryId){
        this.categoryService.delete(categoryId);
//        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted", true), HttpStatus.ACCEPTED);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, null);
    }
}
