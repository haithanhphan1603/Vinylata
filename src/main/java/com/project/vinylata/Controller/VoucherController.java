package com.project.vinylata.Controller;

import com.project.vinylata.DTO.VoucherDto;
import com.project.vinylata.Model.Voucher;
import com.project.vinylata.Repository.VoucherRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherRepository voucherRepository;
    @GetMapping("/api/voucher/user/all")
    public ResponseEntity<Object> getAllVoucher(){
        //list all voucher has not been expired yet.
        List<VoucherDto> voucherDtoList = voucherService.getAllVoucherForUser();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, voucherDtoList);
    }

    @GetMapping("/api/voucher/admin/all")
    public ResponseEntity<Object> getAllVoucherForAdmin(){
        //list all voucher has not been expired yet.
        List<VoucherDto> voucherDtoList = voucherService.getAllVoucherForAdmin();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, voucherDtoList);
    }

    @GetMapping("/api/voucher/admin/expired")
    public ResponseEntity<Object> getAllExpiredVoucherForAdmin(){
        List<VoucherDto> voucherDtoList = voucherService.getAllExpiredVoucherForAdmin();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, voucherDtoList);
    }

    @GetMapping("/api/voucher/admin/valid")
    public ResponseEntity<Object> getAllValidVoucherForAdmin(){
        List<VoucherDto> voucherDtoList = voucherService.getAllValidVoucherForAdmin();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, voucherDtoList);
    }

    @PostMapping("/api/voucher/admin/add")
    public ResponseEntity<Object> addNewVoucher(@RequestBody VoucherDto voucherDto){
        voucherService.add(voucherDto);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "a new voucher has been added successfully!");
    }

    @DeleteMapping("/api/voucher/admin/delete/{id}")
    public ResponseEntity<Object> deleteVoucherById(@PathVariable long id){
        voucherService.deleteById(id);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, "voucher " + id + "has been removed successfully!");
    }

    @PutMapping("/api/voucher/admin/update/{id}")
    public ResponseEntity<Object> updateVoucher(@RequestBody VoucherDto voucherDto){
        voucherService.saveUpdate(voucherDto);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "the voucher has been updated successfully!");
    }
}
