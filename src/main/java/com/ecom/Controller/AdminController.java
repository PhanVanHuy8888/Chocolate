package com.ecom.Controller;

import com.ecom.Model.Category;
import com.ecom.Model.Product;
import com.ecom.Repository.CateRepo;
import com.ecom.Service.CateService;
import com.ecom.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CateService cateService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ProductService productService;

    @Autowired
    private CateRepo cateRepo;

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }


//    Product
    @GetMapping("/listProduct")
    public String listProduct(Model m) {
        m.addAttribute("listProduct", productService.getAllProduct());
        return "admin/listProduct";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model m) {
        List<Category> categories = cateService.getAllCate();
        m.addAttribute("categories", categories);
        return "admin/addProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session) throws IOException {
        if (image.isEmpty()) {
            // If no image is uploaded, set a default image name
            product.setImage("default.jpg");
        } else {
            // Set the image name to the original filename
            product.setImage(image.getOriginalFilename());
            try {
                // Save the image to the filesystem
                Path imagePath = Paths.get("src/main/resources/static/img/product_img/" + image.getOriginalFilename());
                Files.createDirectories(imagePath.getParent()); // Ensure directories exist
                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // Handle IO exception
                session.setAttribute("errorMsg", "Failed to save image: " + e.getMessage());
                return "redirect:/admin/listProduct";
            }
        }

        // Save the product to the database
        Product savedProduct = productService.saveProduct(product);
        if (savedProduct != null) {
            session.setAttribute("succMsg", "Product saved successfully");
        } else {
            session.setAttribute("errorMsg", "Failed to save product");
        }

        return "redirect:/admin/listProduct";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductById(id));
        m.addAttribute("categories", cateService.getAllCate());
        return "/admin/editProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image, HttpSession session) {
        Product updateP = productService.updateProduct(product, image);
        if(ObjectUtils.isEmpty(updateP)) {
            session.setAttribute("errorMsg", "Failed to save product");
        }

        return "redirect:/admin/listProduct";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id,HttpSession session) {
        Boolean delProduct = productService.deleteProduct(id);
        if(delProduct) {
            session.setAttribute("succMsg", "Delete successfully");
        }else
            session.setAttribute("errorMsg", "Delete fail Product !");

        return "redirect:/admin/listProduct";
    }

    //    Category
    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }

    @GetMapping("/listCategory")
    public String listCategory(Model m) {
        m.addAttribute("listCate", cateService.getAllCate());
        return "admin/listCategory";
    }

    @PostMapping("/saveCategory")
    public String saveCate(@ModelAttribute Category category, HttpSession session) {
        Boolean existCate = cateService.existCate(category.getName());
        if(existCate) {
            session.setAttribute("errorMsg", "Category Name already exists");
            return "redirect:/admin/category";
        }else {
            Category saveCate = cateService.saveCategory(category);
            if(ObjectUtils.isEmpty(saveCate)) {
                session.setAttribute("errorMsg", "Not saved ! internal server error");
                return "redirect:/admin/category";
            }else {
                session.setAttribute("succMsg", "Saved successfully");
            }
        }


        return "redirect:/admin/listCategory";
    }
    @GetMapping("/deleteCate/{id}")
    public String deleteCate(@PathVariable int id, HttpSession session) {
        Boolean delCate = cateService.deleteCate(id);
        if(delCate) {
            session.setAttribute("succMsg", "Delete successfully");
        }else
            session.setAttribute("errorMsg", "Delete fail Category !");

        return "redirect:/admin/listCategory";
    }

    @GetMapping("/editCate/{id}")
    public String editCate(@PathVariable int id, Model m) {
        m.addAttribute("cate", cateService.getCateById(id));
        return "admin/editCategory";

    }
    @GetMapping("/getCateById/{id}")
    public Category getCate(@PathVariable int id) {
        Category category = cateRepo.findById(id).get();
        return category;
    }
    @PostMapping("/updateCategory")
    public String updateCate(@ModelAttribute Category category, HttpSession session) {
        Category cate = cateService.getCateById(category.getId());
        if(!ObjectUtils.isEmpty(category)) {
            cate.setName(category.getName());
        }
        Category updateCate = cateService.saveCategory(cate);

        if(ObjectUtils.isEmpty(category)) {
            session.setAttribute("errorMsg", "Update Category Fail !");
        }

        return "redirect:/admin/listCategory" ;
    }
}
