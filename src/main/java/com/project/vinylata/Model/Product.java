package com.project.vinylata.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@lombok.Setter
@lombok.Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)
    private Long id;
    private String productTitle;
    private String productAttributes;
    private String productImage;
    private boolean productStatus;
    private Double productPricing;
    @Column(length = 512)
    private String productDescription;
    @Column(length = 512)
    private String productDetail;
    private String productSlug;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne
    private Vendor vendor;
    public void map(Object o) {
    }

}
