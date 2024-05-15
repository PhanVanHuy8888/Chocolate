package com.ecom.Service;

import com.ecom.Model.Product;
import com.ecom.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepo.findById(id).orElse(null);
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {
        Product p = getProductById(product.getId());
        String imgName = image.isEmpty() ? p.getImage() : image.getOriginalFilename();
        p.setTitle(product.getTitle());
        p.setImage(imgName);
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setCategory(product.getCategory());

        Product updateProduct = productRepo.save(p);

        if (!ObjectUtils.isEmpty(updateProduct)) {
            if (!image.isEmpty()) {
                try {
                    Path imagePath = Paths.get("src/main/resources/static/img/product_img/" + image.getOriginalFilename());
                    Files.createDirectories(imagePath.getParent()); // Ensure directories exist
                    Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return product;
        }
        return product;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        List<Product> products = null;
        if(ObjectUtils.isEmpty(category)) {
            products = productRepo.findByIsActiveTrue();
        }else {
            productRepo.findByCategory(category);
        }
        return products;
    }


    @Override
    public Boolean deleteProduct(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(product)) {
            productRepo.delete(product);
            return true;
        }
        return false;
    }
}
