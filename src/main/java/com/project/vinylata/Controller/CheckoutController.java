package com.project.vinylata.Controller;

import com.project.vinylata.DTO.OrderDto;
import com.project.vinylata.DTO.OrderItemDto;
import com.project.vinylata.DTO.OrderUserDto;
import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Exception.EmailMessageException;
import com.project.vinylata.Model.*;
import com.project.vinylata.Repository.*;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.EmailService;
import com.project.vinylata.Service.VoucherService;
import jakarta.mail.MessagingException;
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
@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class CheckoutController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/user-info")
    public ResponseEntity<Object> checkout(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseHandler.responseBuilder("oke", HttpStatus.OK, new UserDto());
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.get().getFirstName());
        userDto.setLastName(user.get().getLastName());
        userDto.setEmail(user.get().getEmail());
        userDto.setPhoneNumber(user.get().getPhoneNumber());
        userDto.setAddress(user.get().getAddress());

        return ResponseHandler.responseBuilder("oke", HttpStatus.OK, userDto);
    }


    @PostMapping("/confirm")
    public ResponseEntity<Object> confirm(@RequestBody OrderDto orderDto){

        Order order = new Order();
        orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDto> orderItemDtos = orderDto.getItemDtoList();
        for (OrderItemDto each: orderItemDtos){
            OrderItem orderItem = new OrderItem();
            orderItem.setId(each.getId());
            orderItem.setName(each.getName());
            orderItem.setPrice(each.getPrice());
            orderItem.setArtist(each.getArtist());
            orderItem.setImage(each.getImage());
            orderItem.setQuantity(each.getQuantity());
            orderItems.add(orderItem);

            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
        order.setItemList(orderItems);
        OrderUserDto dto = orderDto.getOrderUserDto();
        order.setAddress(dto.getAddress());
        order.setOrderUserName(dto.getUserName());
        order.setOrderUserEmail(dto.getEmail());
        order.setOrderUserPhone(dto.getPhoneNumber());

        order.setTotalPrice(orderDto.getTotalPrice());
        order.setCreatedDate(new Date());
        order.setOrderStatus("waiting for confirmation");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            order.setUser(null);
        }else {
            order.setUser(user.get());
        }
        orderRepository.save(order);

        // Process the order and generate orderDetails string
        StringBuilder orderItemList = new StringBuilder();
        for (OrderItemDto orderItem: orderDto.getItemDtoList()){
            orderItemList.append(orderItem.getName());
            orderItemList.append(", ");
        }

        String orderDetails = "Order ID: " + order.getId() +
                "<br/>" + "Order User: " + order.getOrderUserName() +
                "<br/>" + "Order item list: " + orderItemList +
                "<br/>" + "Order Payment: " + order.getTotalPrice() +
                "<br/>" + "Order Address: " + order.getAddress() +
                "<br/>" + "Order Date: " + order.getCreatedDate();
        // Send email to the customer
        try {
            emailService.sendOrderConfirmationEmail(orderDto.getOrderUserDto().getEmail(), orderDetails);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailMessageException("failed to send message!");
        }

        return ResponseHandler.responseBuilder("oke", HttpStatus.OK, "order has been confirmed to process!");
    }
}
