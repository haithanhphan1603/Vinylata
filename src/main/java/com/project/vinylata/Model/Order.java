package com.project.vinylata.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itemList;

    private String address;

    private String orderUserName;

    private String orderUserEmail;

    private String orderUserPhone;

    private double totalPrice;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    private User user;

    private String orderStatus;
}
