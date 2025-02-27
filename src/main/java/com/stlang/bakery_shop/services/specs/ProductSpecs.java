package com.stlang.bakery_shop.services.specs;


import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.domains.Product_;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecs {

    public static Specification<Product> nameLike(String productName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Product_.NAME), "%" + productName + "%");
    }

    public static Specification<Product> targetEqual(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .in(root.get(Product_.TARGET)).value(target);
    }

    public static Specification<Product> minPrice(Double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .ge(root.get(Product_.PRICE), minPrice);
    }

    public static Specification<Product> maxPrice(Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .le(root.get(Product_.PRICE), maxPrice);
    }

    public static Specification<Product> matchPrice(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .and(
                        criteriaBuilder.ge(root.get(Product_.PRICE), minPrice),
                        criteriaBuilder.le(root.get(Product_.PRICE), maxPrice));

    }

    public static Specification<Product> matchPriceWithBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .between(root.get(Product_.PRICE), minPrice, maxPrice);
    }

    public static Specification<Product> findAllSpecByLandPriceRange(List<String> price) {
        Specification<Product> combinedSpec = (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
        int count = 0;
        for (String p : price) {
            double min = 0.0;
            double max = 0.0;
            switch (p) {
                case "duoi-100-nghin":
                    min = 0;
                    max = 100000;
                    count++;
                    break;
                case "tu-100-199":
                    min = 100000;
                    max = 199000;
                    count++;
                    break;
                case "tu-200-299":
                    min = 200000;
                    max = 299000;
                    count++;
                    break;
                case "lon-hon-299":
                    min = 299000;
                    max = 10e10;
                    count++;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rageSpec = ProductSpecs.matchPriceWithBetween(min, max);
                combinedSpec = combinedSpec.or(rageSpec);
            }
        }
        return combinedSpec;
    }


}
