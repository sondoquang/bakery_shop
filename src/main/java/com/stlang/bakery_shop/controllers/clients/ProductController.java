package com.stlang.bakery_shop.controllers.clients;

import com.stlang.bakery_shop.domains.FilterRequest;
import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.services.iservices.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final HttpServletRequest request;

    public ProductController(IProductService productService, HttpServletRequest request) {
        this.productService = productService;
        this.request = request;
    }


    @RequestMapping("/products")
    public String getProductPage(
            Model model,
            @RequestParam("page") Optional<String> pageNo,
            @RequestParam("limit") Optional<String> count,
            FilterRequest filterRequest) {
        int pageSize = count.map(Integer::parseInt).orElse(12);
        int pageNumber = pageNo.map(Integer::parseInt).orElse(1);

        Sort sort = null;
        if(filterRequest.getSort() != null) {
            if(filterRequest.getSort().equals("gia-tang-dan")){
                sort = Sort.by(Sort.Direction.ASC, "price");
            }
            if(filterRequest.getSort().equals("gia-giam-dan")){
                sort = Sort.by(Sort.Direction.DESC, "price");
            }
        }
        Page<Product> page = productService.findAllProducts(filterRequest, pageNumber-1, pageSize,sort );
        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + (page.getNumber()+1), "");
            qs = qs.replace("limit=" + (count), "");
        }
        model.addAttribute("queryString", qs == null ? "" : qs);
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
