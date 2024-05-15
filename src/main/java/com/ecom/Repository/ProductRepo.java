package com.ecom.Repository;

import com.ecom.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByIsActiveTrue();
    List<Product> findByCategory(String category);
}
