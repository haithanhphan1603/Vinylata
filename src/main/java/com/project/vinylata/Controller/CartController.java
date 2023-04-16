package com.project.vinylata.Controller;

import com.project.vinylata.DTO.AddToCartDto;
import com.project.vinylata.DTO.CartDto;
import com.project.vinylata.Exception.CartItemNotExistException;
import com.project.vinylata.Model.Cart;
import com.project.vinylata.Model.CartItem;
import com.project.vinylata.Model.Product;
import com.project.vinylata.Model.User;
import com.project.vinylata.Repository.CartItemRepository;
import com.project.vinylata.Repository.CartRepository;
import com.project.vinylata.Repository.ProductRepository;
import com.project.vinylata.Repository.UserRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.CartItemService;
import com.project.vinylata.Service.CartService;
import com.project.vinylata.Service.ProductService;
import com.project.vinylata.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/")
    public ResponseEntity<Object> cartPage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        Cart cart = cartRepository.findCartByUser(user.get());
        if(cart==null) {
            cart = new Cart();
            cart.setUser(userRepository.getUsersByEmail(username));
            cartRepository.save(cart);
        }
        CartDto cartDto = cartService.listCartItems(cart);
        //oke done
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, cartDto);
    }

    @GetMapping("/addToCart")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartDto addToCartDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser  = userRepository.getUsersByEmail(username);

        Cart cart = cartRepository.findCartByUser(currentUser);
        if(cart==null) {
            cart = new Cart();
            cart.setUser(userRepository.getUsersByEmail(username));
            cartRepository.save(cart);
        }

        Optional<Product> product = Optional.ofNullable(productRepository.findById(addToCartDto.getProductId()));
        if (product.isEmpty()){
            return ResponseHandler.errorResponseBuilder("failure", HttpStatus.BAD_REQUEST, "not found product");
        }
        //handle if product has been in cart already
        CartItem cartItem = cartItemRepository.findByProductAndCart(product.get(), cart);
        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product.get());
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
            return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, "added to cart");
        }else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
            return ResponseHandler.responseBuilder("success", HttpStatus.CREATED, "added to cart one more");
        }
    }

    @PutMapping("/changCartItemQuanity/{id}")
    public ResponseEntity<Object> changeQuanity(@PathVariable String id,@RequestParam String quantity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser  = userRepository.getUsersByEmail(username);

        Cart cart = cartRepository.getCartByUser(currentUser);
        Product product = productRepository.findById(Long.parseLong(id));
        CartItem cartItem = cartItemRepository.findByProductAndCart(product, cart);
        if (cartItem == null){
            throw new CartItemNotExistException("this is not found");
        }
        cartItem.setQuantity(Integer.parseInt(quantity));
        cartItemRepository.save(cartItem);
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, "this quantity has been changed successfully");
    }

    @PostMapping("/deleteCartItem/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser  = userRepository.getUsersByEmail(username);

        Cart cart = cartRepository.getCartByUser(currentUser);
        Product product = productRepository.findById(Long.parseLong(id));
        CartItem cartItem = cartItemRepository.findByProductAndCart(product,cart);

        if (cartItem == null){
            throw new CartItemNotExistException("this is not found");
        }

        cartItemRepository.delete(cartItem);

        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, "cartItem"+ cartItem + "is deleted from cart");
    }
}
