package com.project.vinylata.Service;

import com.project.vinylata.DTO.CartDto;
import com.project.vinylata.DTO.CartItemDto;
import com.project.vinylata.Model.Cart;
import com.project.vinylata.Model.CartItem;
import com.project.vinylata.Repository.CartItemRepository;
import com.project.vinylata.Repository.CartRepository;
import com.project.vinylata.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository  cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public CartDto listCartItems(Cart cart) {
        List<CartItem> cartItemList = cartItemRepository.findByCart(cart);
        CartDto cartDto = new CartDto();
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        double totalCost = 0;
        for (CartItem ci: cartItemList) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(ci.getId());
            cartItemDto.setProductId(ci.getProduct().getId());
            cartItemDto.setUnitPrice(productRepository.findById(ci.getProduct().getId()).getProductPricing());
            cartItemDto.setQuantity(ci.getQuantity());
            cartItemDto.setTotalPaymentEachCartItem(ci.getQuantity()*ci.getProduct().getProductPricing());
            cartItemDtos.add(cartItemDto);
            totalCost += ci.getQuantity()*ci.getProduct().getProductPricing();
        }
        cartDto.setCartItemDtos(cartItemDtos);
        cartDto.setTotalMoney(totalCost);
        return cartDto;
    }
}
