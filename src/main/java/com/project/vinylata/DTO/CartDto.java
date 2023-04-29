package com.project.vinylata.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItemDtos;
    private double totalMoney;

    private long voucherId;

}
