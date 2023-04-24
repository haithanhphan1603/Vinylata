package com.project.vinylata.DTO;

import com.project.vinylata.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;

    private Long productId;

    private double unitPrice;

    private int quantity;

    private double totalPaymentEachCartItem;

    //private Cart cart;
}
