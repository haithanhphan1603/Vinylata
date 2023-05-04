package com.project.vinylata.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.vinylata.Model.User;
import com.project.vinylata.Model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private List<OrderItemDto> itemDtoList;

    private OrderUserDto orderUserDto;

    private double totalPrice;

    private String orderStatus;
}
