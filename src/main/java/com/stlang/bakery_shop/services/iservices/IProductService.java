package com.stlang.bakery_shop.services.iservices;


import com.stlang.bakery_shop.domains.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<Product> findAllProducts(int pageNumber, int pageSize);
    Product findProductById(long id);
    Boolean existsByName(String name);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    int deleteProduct(long id);

}
