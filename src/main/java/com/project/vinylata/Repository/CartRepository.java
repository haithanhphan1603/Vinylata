package com.project.vinylata.Repository;

import com.project.vinylata.Model.Cart;
import com.project.vinylata.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);

    Cart getCartByUser(User user);
}
