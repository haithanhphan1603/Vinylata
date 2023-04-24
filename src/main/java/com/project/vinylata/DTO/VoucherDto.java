package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {
    private long id;

    private String code;

    private int quantity;

    private double discount;
    private Date expiratedDate;
}
