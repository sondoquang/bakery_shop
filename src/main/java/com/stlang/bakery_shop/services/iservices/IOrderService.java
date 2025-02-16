package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.OrderDetail;

public interface IOrderService {
    Order createOrder(String email, Order Order);
}
