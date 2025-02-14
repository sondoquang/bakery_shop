package com.stlang.bakery_shop.repositories;


import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.CartDetail;
import com.stlang.bakery_shop.domains.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends CrudRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartAndProduct(Cart cart, Product product);
}
