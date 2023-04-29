package com.project.vinylata.Repository;

import com.project.vinylata.Model.Cart;
import com.project.vinylata.Model.CartItem;
import com.project.vinylata.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductAndCart(Product product, Cart cart);

    List<CartItem> findByCart(Cart cart);
}
