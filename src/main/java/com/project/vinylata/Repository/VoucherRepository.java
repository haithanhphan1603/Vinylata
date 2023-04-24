package com.project.vinylata.Repository;

import com.project.vinylata.DTO.VoucherDto;
import com.project.vinylata.Model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    //List<VoucherDto> findById(long id);

    void deleteVoucherById(long id);

    List<VoucherDto> findVoucherByCode(String code);

}
