package com.project.vinylata.Repository;

import com.project.vinylata.Model.Order;
import com.project.vinylata.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByOrderStatus(String status);


    public List<Order> findByUser(User user);

    public Order findOrderByUserAndId(User user, long id);

    public int countByOrderStatus(String status);
}
