package com.ecom.Controller;

import com.ecom.Model.Category;
import com.ecom.Model.Product;
import com.ecom.Service.CateService;
import com.ecom.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CateService cateService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model m) {
        List<Product> products = productService.getAllProduct();
        m.addAttribute("lstProduct", products);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/product")
    public String product(Model m, @RequestParam(value = "category", defaultValue = "") String category) {
        List<Category> categories = cateService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts(category);
        m.addAttribute("lstProduct", products);
        m.addAttribute("listCate", categories);
        return "product";
    }

    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductById(id));
        return "viewProduct";
    }

    @GetMapping("/view")
    public String productDetail() {

        return "viewProduct";
    }
}
