package com.project.vinylata.Service;

import com.project.vinylata.DTO.OrderDto;
import com.project.vinylata.DTO.OrderItemDto;
import com.project.vinylata.DTO.OrderUserDto;
import com.project.vinylata.Exception.EmailMessageException;
import com.project.vinylata.Model.Order;
import com.project.vinylata.Model.OrderItem;
import com.project.vinylata.Model.User;
import com.project.vinylata.Repository.OrderRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSevice {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public List<OrderDto> getMyOrder(User user){
        List<Order> orders = orderRepository.findByUser(user);
        return filter(orders);
    }

    public List<OrderDto> getUnconfirmedOrder(){
        List<Order> orders = orderRepository.findByOrderStatus("waiting for confirmation!");
        return filter(orders);
    }

    public List<OrderDto> getConfirmedOrder(){
        List<Order> orders = orderRepository.findByOrderStatus("confirmed");
        return filter(orders);
    }

    public void acceptOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            //handle exception
        }
        Order findedOrder = order.get();
        if (order.get().getOrderStatus().equals("waiting for confirmation!")){
            //handle exception
        }
        findedOrder.setOrderStatus("confirmed");
        StringBuilder orderItemList = new StringBuilder();
        for (OrderItem orderItem: findedOrder.getItemList()){
            orderItemList.append(orderItem.getName());
            orderItemList.append(", ");
        }

        String orderDetails = "Order ID: " + findedOrder.getId() +
                "<br/>" + "Order User: " + findedOrder.getOrderUserName() +
                "<br/>" + "Order item list: " + orderItemList +
                "<br/>" + "Order Payment: " + findedOrder.getTotalPrice() +
                "<br/>" + "Order Address: " + findedOrder.getAddress() +
                "<br/>" + "Order Date: " + findedOrder.getCreatedDate();
        // Send email to the customer
        try {
            emailService.sendOfficialOrderConfirmationEmail(findedOrder.getOrderUserEmail(), orderDetails);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailMessageException("failed to send message to customer!");
        }

        orderRepository.save(findedOrder);
    }

    public void cancelOrderById(long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            //handle exception
        }
        Order findedOrder = order.get();
        if (order.get().getOrderStatus().equals("waiting for confirmation!")){
            //handle exception
        }
        findedOrder.setOrderStatus("canceled");
        StringBuilder orderItemList = new StringBuilder();
        for (OrderItem orderItem: findedOrder.getItemList()){
            orderItemList.append(orderItem.getName());
            orderItemList.append(", ");
        }

        String orderDetails = "Order ID: " + findedOrder.getId() +
                "<br/>" + "Order User: " + findedOrder.getOrderUserName() +
                "<br/>" + "Order item list: " + orderItemList +
                "<br/>" + "Order Payment: " + findedOrder.getTotalPrice() +
                "<br/>" + "Order Address: " + findedOrder.getAddress() +
                "<br/>" + "Order Date: " + findedOrder.getCreatedDate();
        // Send email to the customer
        try {
            emailService.sendCanceledOrderConfirmationEmail(findedOrder.getOrderUserEmail(), orderDetails);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailMessageException("failed to send message to customer!");
        }
        orderRepository.save(findedOrder);
    }

    public void cancelOrderByUserAndId(User user, long id){

        Order order = orderRepository.findOrderByUserAndId(user, id);
        if (order == null){
            //handle exception
        }
        if (order.getOrderStatus().equals("waiting for confirmation!")){
            //handle exception
        }
        order.setOrderStatus("canceled");
        orderRepository.save(order);
    }

    private List<OrderDto> filter(List<Order> orders){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order: orders){
            OrderUserDto orderUserDto =
                    new OrderUserDto(order.getOrderUserName(), order.getOrderUserPhone(), order.getOrderUserEmail(), order.getAddress());
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setOrderUserDto(orderUserDto);
            orderDto.setTotalPrice(order.getTotalPrice());

            //list
            List<OrderItem> orderItems = order.getItemList();
            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem: orderItems){
                orderItemDtos.add(new OrderItemDto(orderItem.getId(), orderItem.getImage(), orderItem.getName(), orderItem.getPrice(), orderItem.getArtist(), orderItem.getQuantity()));
            }
            orderDto.setItemDtoList(orderItemDtos);
            orderDto.setOrderStatus(order.getOrderStatus());

            //add to list
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
