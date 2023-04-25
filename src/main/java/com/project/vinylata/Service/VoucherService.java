package com.project.vinylata.Service;

import com.project.vinylata.DTO.VoucherDto;
import com.project.vinylata.Exception.VoucherNotFoundException;
import com.project.vinylata.Model.Voucher;
import com.project.vinylata.Repository.VoucherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher findVoucherById(long id){
        Optional<Voucher> voucher = voucherRepository.findById(id);
        return voucher.get();
    }

    public void saveUpdate(VoucherDto voucherDto){
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherDto.getId());
        if (voucherOptional.isEmpty()){
            throw new VoucherNotFoundException("this voucher not found");
        }
        Voucher voucher = voucherOptional.get();
        voucher.setCode(voucherDto.getCode());
        voucher.setQuantity(voucherDto.getQuantity());
        voucher.setDiscount(voucherDto.getDiscount());
        voucher.setExpiratedDate(voucherDto.getExpiratedDate());
        voucherRepository.save(voucher);
    }

    public List<VoucherDto> getAllVoucherForUser(){
        //list all voucher has not been expired yet.
        List<Voucher> voucherList = voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher: voucherList){
            if (!isExpired(voucher)){
                voucherDtoList
                        .add(new VoucherDto(voucher.getId(), voucher.getCode(),
                        voucher.getQuantity(), voucher.getDiscount(),
                        voucher.getExpiratedDate()));
            }
        }
        return voucherDtoList;
    }

    public List<VoucherDto> getAllVoucherForAdmin(){
        List<Voucher> voucherList = voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher: voucherList){
                voucherDtoList
                        .add(new VoucherDto(voucher.getId(), voucher.getCode(),
                                voucher.getQuantity(), voucher.getDiscount(),
                                voucher.getExpiratedDate()));
        }
        return voucherDtoList;
    }

    public List<VoucherDto> getAllExpiredVoucherForAdmin(){
        //list all voucher has been expired already.
        List<Voucher> voucherList = voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher: voucherList){
            if (isExpired(voucher)){
                voucherDtoList
                        .add(new VoucherDto(voucher.getId(), voucher.getCode(),
                                voucher.getQuantity(), voucher.getDiscount(),
                                voucher.getExpiratedDate()));
            }
        }
        return voucherDtoList;
    }

    public List<VoucherDto> getAllValidVoucherForAdmin(){
        List<Voucher> voucherList = voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher: voucherList){
            if (!isExpired(voucher)){
                voucherDtoList
                        .add(new VoucherDto(voucher.getId(), voucher.getCode(),
                                voucher.getQuantity(), voucher.getDiscount(),
                                voucher.getExpiratedDate()));
            }
        }
        return voucherDtoList;
    }

    public void add(VoucherDto voucherDto){
        Voucher voucher = new Voucher();
        voucher.setCode(voucherDto.getCode());
        voucher.setQuantity(voucherDto.getQuantity());
        voucher.setDiscount(voucherDto.getDiscount());
        voucher.setExpiratedDate(voucherDto.getExpiratedDate());
        voucherRepository.save(voucher);
    }

    public void deleteById(long id){
        if (voucherRepository.findById(id).isEmpty()){
            throw new VoucherNotFoundException("this voucher not found");
        }
        voucherRepository.deleteVoucherById(id);
    }

    public double getDiscountById(long id){
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucherRepository.findById(id).isEmpty() || isExpired(voucher.get())){
            return 0;
        }
        return voucher.get().getDiscount();
    }

    public int getQuantityById(long id){
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucherRepository.findById(id).isEmpty()){
            return 0;
        }
        return voucher.get().getQuantity();
    }

    public void decreaseQuantity(long id){
        if (getQuantityById(id) > 0){
            Optional<Voucher> voucher = voucherRepository.findById(id);
            voucher.get().setQuantity(voucher.get().getQuantity() - 1);
            voucherRepository.save(voucher.get());
        }
    }

    public boolean isExpired(Voucher voucher){
       return voucher.getExpiratedDate().before(new Date());
    }
}
