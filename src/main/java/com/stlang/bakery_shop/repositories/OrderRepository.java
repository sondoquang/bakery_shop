package com.stlang.bakery_shop.repositories;

import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByPaymentRef(String paymentRef);
    List<Order> findByIsActive(Boolean isActive);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE o.isActive = true")
    Integer getTotalOrders();
}
