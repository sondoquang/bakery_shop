package com.stlang.bakery_shop.services.iservices;


import com.stlang.bakery_shop.domains.CartDetail;

public interface ICartDetailService {
    void removeItemFromCartDetail(long cartId, long productId);
    CartDetail updateQuantity(long cartId, long productId, int quantity);
    Double getTotalAmount(long cartId);
}
