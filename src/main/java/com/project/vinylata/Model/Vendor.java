package com.project.vinylata.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@lombok.Setter
@lombok.Getter
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String vendorName;
//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
//    private List<Product> products;
}
