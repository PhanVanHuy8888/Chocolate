package com.ecom.Repository;

import com.ecom.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateRepo extends JpaRepository<Category, Integer> {
    public Boolean existsByName(String name);
    public List<Category> findByIsActiveTrue();
}
