package com.ecom.Service;

import com.ecom.Model.Category;
import com.ecom.Repository.CateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
public class CateServiceImpl implements CateService{
    @Autowired
    private CateRepo cateRepo;
    @Override
    public Category saveCategory(Category category) {
        return cateRepo.save(category);
    }

    @Override
    public Boolean existCate(String name) {
        return cateRepo.existsByName(name);
    }

    @Override
    public List<Category> getAllCate() {
        return cateRepo.findAll();
    }

    @Override
    public Category getCateById(int id) {
        Category category = cateRepo.findById(id).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllActiveCategory() {
        List<Category> categories = cateRepo.findByIsActiveTrue();
        return categories;
    }

    @Override
    public Boolean deleteCate(int id) {
        Category category = cateRepo.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(category)) {
            cateRepo.delete(category);
            return true;
        }
        return false;
    }
}
