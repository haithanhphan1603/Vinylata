package com.project.vinylata.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    @NotNull
    private String productTitle;
    @NotNull
    private String productAttributes;
    @NotNull
    private String productImage;
    @NotNull
    private boolean productStatus;
    @NotNull
    private Double productPricing;
    @NotNull
    @Column(length = 512)
    private String productDescription;
    @NotNull
    @Column(length = 512)
    private String productDetail;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendor vendor;
    public void map(Object o) {
    }

}