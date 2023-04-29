package com.project.vinylata.Controller;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.DTO.VendorDto;
import com.project.vinylata.Exception.VendorAlreadyExistsException;
import com.project.vinylata.Model.Vendor;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.VendorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/vendors")
public class VendorController {
    static final String SUCCESS_MESSAGE = "success";
    static final String FAIL_MESSAGE = "failure";
    @Autowired
    private VendorService vendorService;

    @PostMapping("/add")
    public ResponseEntity<Object> create(@RequestBody VendorDto vendor) {
            return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.CREATED,this.vendorService.create(vendor));
    }

    @GetMapping("/")
    public ResponseEntity<Object> show(){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.vendorService.show());
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<Object> showById(@PathVariable Long vendorId){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.vendorService.showById(vendorId));
    }

    @PutMapping("/update/{vendorId}")
    public ResponseEntity<Object> update(@RequestBody VendorDto newVendor, @PathVariable Long vendorId){
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, this.vendorService.update(newVendor, vendorId));
    }

    @DeleteMapping("/del/{vendorId}")
    public ResponseEntity<Object> delete(@PathVariable Long vendorId){
        this.vendorService.delete(vendorId);
        return ResponseHandler.responseBuilder(SUCCESS_MESSAGE, HttpStatus.ACCEPTED, null);
    }
}
