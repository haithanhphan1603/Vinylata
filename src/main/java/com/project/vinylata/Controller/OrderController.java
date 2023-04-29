package com.project.vinylata.Controller;

import com.project.vinylata.DTO.ManagedOrderDto;
import com.project.vinylata.Model.User;
import com.project.vinylata.Repository.UserRepository;
import com.project.vinylata.Response.ResponseHandler;
import com.project.vinylata.Service.OrderSevice;
import jakarta.websocket.OnClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class OrderController {

    @Autowired
    private OrderSevice orderSevice;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/order/admin/unconfirmed")
    public ResponseEntity<Object> getUnconfirmedOrder(){

        List<ManagedOrderDto> list =orderSevice.getUnconfirmedOrder();

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, list);
    }

    @GetMapping("/api/order/admin/confirmed")
    public ResponseEntity<Object> getConfirmedOrder(){
        List<ManagedOrderDto> list =orderSevice.getConfirmedOrder();
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, list);
    }

    @GetMapping("/api/order/user/myorder")
    public ResponseEntity<Object> getMyOrder(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            return ResponseHandler.errorResponseBuilder("bad", HttpStatus.BAD_REQUEST, "not found user!");
        }
        List<ManagedOrderDto> list =orderSevice.getMyOrder(user.get());
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, list);
    }

    @PostMapping("/api/order/admin/accept/{id}")
    public ResponseEntity<Object> acceptOrder(@PathVariable long id){
        orderSevice.acceptOrder(id);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "order has been confirmed");
    }


    @PostMapping("/api/order/admin/cancel/{id}")
    public ResponseEntity<Object> adminCancelOrder(@PathVariable long id){
        orderSevice.cancelOrderById(id);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, "order has been canceled");
    }

    @PostMapping("/api/order/user/cancel/{id}")
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
