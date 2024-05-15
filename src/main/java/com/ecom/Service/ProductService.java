package com.ecom.Service;

import com.ecom.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);
    public List<Product> getAllProduct();
    public Product getProductById(int id);
    public Product updateProduct(Product product, MultipartFile file);
    public Boolean deleteProduct(int id);
    public List<Product> getAllActiveProducts(String category);
}
