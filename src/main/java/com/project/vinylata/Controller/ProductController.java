package com.project.vinylata.Controller;


import com.project.vinylata.DTO.AppConstants;
import com.project.vinylata.DTO.ProductDto;
import com.project.vinylata.Exception.ProductAlreadyExistsException;
import com.project.vinylata.Repository.CategoryRepository;
import com.project.vinylata.Repository.ProductRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add/")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody ProductDto product,
                                         @RequestParam(value = "cateId") Long categoryId,
                                         @RequestParam(value = "vendorId") Long vendorId,
                                         BindingResult newProduct)
            throws ProductAlreadyExistsException {
        if (newProduct.hasErrors()) {
            return ResponseHandler.errorResponseBuilder("failure", HttpStatus.NOT_FOUND, "Product Already Existed");
        } else {
            return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, productService.create(product, categoryId, vendorId));
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> show(@RequestParam(value = "pageNumber") int pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                       @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_ID, required = false) String sortBy,
                                       @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.show(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> showById(@PathVariable Long productId) {
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.showById(productId));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Object> update(@PathVariable Long productId, @RequestBody ProductDto newProduct) {

        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, productService.update(productId, newProduct));

    }

    @DeleteMapping("/del/{productId}")
    public ResponseEntity<Object> delete(@PathVariable int productId) {
        productService.delete(productId);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, null);
    }


}

