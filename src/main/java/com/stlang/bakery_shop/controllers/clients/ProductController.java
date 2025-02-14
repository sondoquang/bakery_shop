package com.stlang.bakery_shop.controllers.clients;

import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.services.iservices.IProductService;
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
        int limit = count.map(Integer::parseInt).orElse(12);
        int page = pageNo.map(Integer::parseInt).orElse(1);
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Product> list = productService.findAllProducts(pageable);
        List<Product> products = list.getContent();
        model.addAttribute("products", products);
        model.addAttribute("pageNo", page);
        model.addAttribute("size", list.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("totalElements", list.getTotalElements());
        return "client/product-view";
    }

    @RequestMapping("/product-detail/{productId}")
    public String getProductDetailPage(Model model, @PathVariable int productId) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);
        return "/client/product-detail-view";
    }
}
