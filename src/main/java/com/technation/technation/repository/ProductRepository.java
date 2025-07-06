package com.technation.technation.repository;

import com.technation.technation.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(int categoryId);

    List<Product> findByBrandId(int brandId);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
