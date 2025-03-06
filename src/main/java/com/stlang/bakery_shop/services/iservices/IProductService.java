package com.stlang.bakery_shop.services.iservices;


import com.stlang.bakery_shop.domains.FilterRequest;
import com.stlang.bakery_shop.domains.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IProductService {

    Page<Product> findAllProducts(int pageNumber, int pageSize,String...productName);
    Page<Product> findAllProducts(FilterRequest filterRequest, int pageNumber, int pageSize, Sort sort);
    Product findProductById(long id);
    Boolean existsByName(String name);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    int deleteProduct(long id);
    int getTotalProducts();

}
