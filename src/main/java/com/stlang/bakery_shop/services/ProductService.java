package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.repositories.ProductRepository;
import com.stlang.bakery_shop.services.iservices.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
           return productRepository.save(product);
        }
        return null;
    }

    @Override
    public int deleteProduct(long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return 1;
        } else
            return -1;
    }
}
