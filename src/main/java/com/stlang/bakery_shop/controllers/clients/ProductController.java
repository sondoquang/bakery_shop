package com.stlang.bakery_shop.controllers.clients;

import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.services.iservices.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @RequestMapping("/products")
    public String getProductPage(
            Model model,
            @RequestParam("page") Optional<String> pageNo,
            @RequestParam("limit") Optional<String> count) {
        int pageSize = count.map(Integer::parseInt).orElse(12);
        int pageNumber = pageNo.map(Integer::parseInt).orElse(1);
        Page<Product> page = productService.findAllProducts(pageNumber, pageSize);
        model.addAttribute("page",page);
        return "client/product-view";
    }

    @RequestMapping("/product-detail/{productId}")
    public String getProductDetailPage(Model model, @PathVariable int productId) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);
        return "/client/product-detail-view";
    }
}
