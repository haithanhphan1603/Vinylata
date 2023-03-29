package com.project.vinylata.Controller;


import com.project.vinylata.DTO.AppConstants;
import com.project.vinylata.DTO.ProductDto;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Repository.ProductRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add/{categoryId}")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody ProductDto product, @PathVariable Long categoryId){
//        ProductDto newProduct = productService.create(product, categoryId);
//        return new ResponseEntity<ProductDto>(newProduct, HttpStatus.CREATED);
        return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, productService.create(product, categoryId));
    }

    @GetMapping("/")
    public ResponseEntity<Object> show(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_ID, required = false) String sortBy,
                                                 @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
//        ProductResponse productResponse = productService.show(pageNumber, pageSize, sortBy, sortDir);
//        return productResponse;
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.show(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> showById(@PathVariable Long productId){

        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.showById(productId));

//        ProductDto detailProduct = productService.showById(productId);
//        return new ResponseEntity<ProductDto>(detailProduct,HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Object> update(@PathVariable Long productId, @RequestBody ProductDto newProduct){

        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.update(productId, newProduct));

//        ProductDto updatedProduct = productService.update(productId, newProduct);
//        return new ResponseEntity<ProductDto>(updatedProduct,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/del/{productId}")
    public ResponseEntity<Object> delete(@PathVariable int productId){
        productService.delete(productId);
//        return new ResponseEntity<String>("Product Deleted", HttpStatus.ACCEPTED);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED,null);
    }

}

