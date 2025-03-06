package com.stlang.bakery_shop.services.iservices;

import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.OrderDetail;
import com.stlang.bakery_shop.domains.enums.PaymentStatus;

import java.util.List;

public interface IOrderService {
    List<Order> findByIsActive(Boolean isActive);

    Order findById(Long id);

    Order update(Order order);

    Order createOrder(String email, Order Order, String uuidPaymentRef);

    Order updatePaymentRef(String paymentRef, PaymentStatus status) ;

    void delete(Long id);
    int getTotalOrders();

    List<Order> findTopOrdersByOrderDate(int pageNo, int pageSize, String orderDate);
}
