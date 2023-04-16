package com.project.vinylata.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.vinylata.Model.User;
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
    private int id;

    private List<OrderItemDto> itemDtoList;

    private String address;

//    private long totalPayment;

    // private Date createdDate;

//    @JsonIgnore
//    private User user;

//    private String orderStatus;
}
