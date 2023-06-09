package com.project.vinylata.Controller;


import com.project.vinylata.DTO.OrderDto;
import com.project.vinylata.Model.User;
import com.project.vinylata.Repository.UserRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.OrderSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderSevice orderSevice;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/unconfirmed")
    public ResponseEntity<Object> getUnconfirmedOrder(){

        List<OrderDto> orderDtoList = orderSevice.getUnconfirmedOrder();

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, orderDtoList);
    }

    @GetMapping("/admin/confirmed")
    public ResponseEntity<Object> getConfirmedOrder(){
        List<OrderDto> orderDtoList =orderSevice.getConfirmedOrder();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, orderDtoList);
    }

    @GetMapping("/user/myorder")
    public ResponseEntity<Object> getMyOrder(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseHandler.errorResponseBuilder("bad", HttpStatus.BAD_REQUEST, "not found user!");
        }
        List<OrderDto> list = orderSevice.getMyOrder(user.get());
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, list);
    }

    @PostMapping("/admin/accept/{id}")
    public ResponseEntity<Object> acceptOrder(@PathVariable long id){
        orderSevice.acceptOrder(id);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "order has been confirmed");
    }


    @PostMapping("/admin/cancel/{id}")
    public ResponseEntity<Object> adminCancelOrder(@PathVariable long id){
        orderSevice.cancelOrderById(id);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "order has been canceled");
    }

    @PostMapping("/user/cancel/{id}")
    public ResponseEntity<Object> userCancelOrder(@PathVariable long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseHandler.errorResponseBuilder("bad", HttpStatus.BAD_REQUEST, "not found user!");
        }
        orderSevice.cancelOrderByUserAndId(user.get(), id);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "order has been canceled");
    }
}
