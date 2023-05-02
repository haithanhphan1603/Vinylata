package com.project.vinylata.Controller;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.CategoryService;
import com.project.vinylata.Service.ProductService;
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
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/add")
    public ResponseEntity<Object> create(@RequestBody CategoryDto category){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.CREATED,this.categoryService.create(category));
    }

    @GetMapping("/")
    public ResponseEntity<Object> show(){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.show());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> showById(@PathVariable Long categoryId) {
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.showById(categoryId));
    }

    @PutMapping("/admin/update/{categoryId}")
    public ResponseEntity<Object> update(@RequestBody CategoryDto newCategory, @PathVariable Long categoryId){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.categoryService.update(newCategory, categoryId));
    }

    @DeleteMapping("/admin/del/{categoryId}")
    public ResponseEntity<Object> delete(@PathVariable Long categoryId){
        this.categoryService.delete(categoryId);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, null);
    }
}
