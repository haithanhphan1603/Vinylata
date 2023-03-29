package com.project.vinylata.Repository;


import com.project.vinylata.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findById(Long CategoryId);
}
