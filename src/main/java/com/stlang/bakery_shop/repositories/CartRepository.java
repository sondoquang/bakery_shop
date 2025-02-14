package com.stlang.bakery_shop.repositories;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
