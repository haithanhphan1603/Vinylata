package com.project.vinylata.Service;

import com.project.vinylata.DTO.CartDto;
import com.project.vinylata.Model.Cart;

public interface ICartService {
    public CartDto listCartItems(Cart cart);
}
