package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.CartDetail;
import com.stlang.bakery_shop.domains.User;

import java.util.Optional;

public interface ICartService {

    Cart addProductToCart (String email, long productId, int quantity);

    Cart findByUser(String email);

}
