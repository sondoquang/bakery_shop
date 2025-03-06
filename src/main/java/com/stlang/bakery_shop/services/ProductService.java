package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.FilterRequest;
import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.repositories.ProductRepository;
import com.stlang.bakery_shop.services.iservices.IProductService;
import com.stlang.bakery_shop.services.specs.ProductSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;



@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findAllProducts(int pageNumber, int pageSize, String...productName) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if(productName.length > 0){
            return productRepository.findAll(ProductSpecs.nameLike(productName[0]), pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllProducts(FilterRequest filterRequest, int pageNumber, int pageSize, Sort sort) {
        Pageable pageable;
        if(sort != null){
            pageable = PageRequest.of(pageNumber, pageSize,sort );
        }else{
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        if(filterRequest.getTarget() == null
                && filterRequest.getSort() == null
                && filterRequest.getPrice() == null
        )
            return productRepository.findAll(pageable);

        Specification<Product> combinedSpec = Specification.where(null);

        if (filterRequest.getTarget() != null && filterRequest.getTarget().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs.targetEqual(filterRequest.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if (filterRequest.getPrice() != null && filterRequest.getPrice().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecs
                    .findAllSpecByLandPriceRange(filterRequest.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        return this.productRepository.findAll(combinedSpec, pageable);
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

    @Override
    public int getTotalProducts() {
        return productRepository.getTotalProducts();
    }


}
