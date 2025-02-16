package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.*;

import java.util.List;

public interface IOrderDetailService {

    List<OrderDetail> createOrderDetails(User user, Order order, Cart cart);

}
