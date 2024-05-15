package com.ecom.Service;

import com.ecom.Model.Category;

import java.util.List;

public interface CateService {
    public Category saveCategory(Category category);
    public Boolean existCate(String name);
    public List<Category> getAllCate();

    public Category getCateById(int id);
    public List<Category> getAllActiveCategory();
    public Boolean deleteCate(int id);
}
