package com.project.vinylata.Controller;

import com.project.vinylata.DTO.CartDto;
import com.project.vinylata.DTO.OrderDto;
import com.project.vinylata.DTO.OrderItemDto;
import com.project.vinylata.DTO.VoucherDto;
import com.project.vinylata.Model.*;
import com.project.vinylata.Repository.*;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.CartService;
import com.project.vinylata.Service.ProductService;
import com.project.vinylata.Service.VoucherService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/order/user")
@RestController
public class CheckoutController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/checkout")
    public ResponseEntity<Object> checkOutPage(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        Cart cart = cartRepository.findCartByUser(user.get());
        if(cart==null) {
            cart = new Cart();
            cart.setUser(userRepository.getUsersByEmail(username));
            cartRepository.save(cart);
        }
        CartDto cartDto = cartService.listCartItems(cart);
        if (cart.getVoucher() == null){
            return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, cartDto);
        }
        cartDto.setVoucherId(cart.getVoucher().getId());
        cartDto.setTotalMoney(cartDto.getTotalMoney() - cartDto.getTotalMoney() * voucherService.getDiscountById(cart.getVoucher().getId()));
        return ResponseHandler.responseBuilder("success", HttpStatus.ACCEPTED, cartDto);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Object> confirm(@RequestBody OrderDto orderDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseHandler.errorResponseBuilder("bad", HttpStatus.BAD_REQUEST, "not found user!");
        }

        Order order = new Order();
        orderRepository.save(order);

        List<OrderItemDto> orderItemDto =  orderDto.getItemDtoList();
        List<OrderItem> orderItems = new ArrayList<>();
        double totalCost = 0;
        for (OrderItemDto each: orderItemDto) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(each.getId());
            orderItem.setProduct(productRepository.findById(each.getProductId()));
            orderItem.setAmount(each.getAmount());
            orderItem.setTotalPaymentEachOrderItem(each.getTotalPaymentEachOrderItem());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            totalCost += each.getTotalPaymentEachOrderItem();
            orderItemRepository.save(orderItem);
        }

        //order.setId(orderDto.getId());
        order.setOrderStatus("waiting for confirmation....");
        order.setUser(user.get());
        order.setAddress(orderDto.getAddress());
        order.setCreatedDate(new Date());
        order.setTotalPayment(totalCost - totalCost*(voucherService.getDiscountById(orderDto.getVoucherId())));
        orderRepository.save(order);

        //and remove everything from cart
        Cart cart = cartRepository.findCartByUser(user.get());
        cart.setVoucher(null);
        cartRepository.save(cart);

        //minus 1 out of quantity
        voucherService.decreaseQuantity(orderDto.getVoucherId());


        return ResponseHandler.responseBuilder("oke", HttpStatus.OK, "order has been confirmed to process!");
    }
}
