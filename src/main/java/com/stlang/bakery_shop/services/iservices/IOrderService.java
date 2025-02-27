package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.OrderDetail;
import com.stlang.bakery_shop.domains.enums.PaymentStatus;

public interface IOrderService {
    Order createOrder(String email, Order Order, String uuidPaymentRef);


    Order updatePaymentRef(String paymentRef, PaymentStatus status) ;
}
