package com.project.vinylata.Model;

import com.project.vinylata.Repository.ProductRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@lombok.Setter
@lombok.Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private String categoryName;
    @NotNull
    private String categoryImage;
    @NotNull
    private String categoryBackground;
    @NotNull
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> product;
}
