package com.project.vinylata.Service;

import com.project.vinylata.DTO.CategoryDto;
import com.project.vinylata.DTO.VendorDto;
import com.project.vinylata.Model.Vendor;
import com.project.vinylata.Repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ModelMapper modelMapper;

    public VendorDto create(VendorDto vendorDto) {
        Vendor vendor = this.modelMapper.map(vendorDto, Vendor.class);
        Vendor saveVendor = this.vendorRepository.save(vendor);
        return this.modelMapper.map(saveVendor, VendorDto.class);
    }

    public List<VendorDto> show() {
        List<Vendor> getList = vendorRepository.findAll();
        List<VendorDto> dtoAll = getList.stream()
                .map(vendor -> this.modelMapper.map(vendor, VendorDto.class))
                .collect(Collectors.toList());
        return dtoAll;
    }

    public VendorDto showById(Long id) {
        Vendor vendorId = this.vendorRepository.findById(id);
        return this.modelMapper.map(vendorId, VendorDto.class);
    }

    public VendorDto update(VendorDto newVendor, Long id) {
        Vendor oldVendor = this.vendorRepository.findById(id);
        oldVendor.setVendorName(newVendor.getVendorName());
        oldVendor.setVendorImage(newVendor.getVendorImage());
        Vendor updatedVendor = this.vendorRepository.save(oldVendor);
        return this.modelMapper.map(updatedVendor, VendorDto.class);
    }

    public void delete(Long id){
        Vendor vendor = this.vendorRepository.findById(id);
        this.vendorRepository.delete(vendor);
    }
}
