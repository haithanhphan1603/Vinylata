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
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private int quantity;

    private double discount;
    private Date expiratedDate;

    @OneToMany(mappedBy = "voucher" ,cascade = CascadeType.DETACH)
    @Transient
    private List<Cart> carts;
}
