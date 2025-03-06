package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.OrderDetail;
import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.domains.enums.OrderStatus;
import com.stlang.bakery_shop.domains.enums.PaymentMethod;
import com.stlang.bakery_shop.domains.enums.PaymentStatus;
import com.stlang.bakery_shop.repositories.CartDetailRepository;
import com.stlang.bakery_shop.repositories.CartRepository;
import com.stlang.bakery_shop.repositories.OrderRepository;
import com.stlang.bakery_shop.repositories.UserRepository;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import com.stlang.bakery_shop.services.iservices.IOrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;


    public OrderService( CartRepository cartRepository, UserRepository userRepository, OrderRepository orderRepository, OrderDetailService orderDetailService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
    }

    @Override
    public List<Order> findByIsActive(Boolean isActive) {
        return orderRepository.findByIsActive(isActive);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order update(Order order) {
        Order existingOrder = orderRepository.findById(order.getId()).orElse(null);
        if(existingOrder != null) {
            order.setNote(existingOrder.getNote());
            order.setIsActive(existingOrder.getIsActive());
            order.setUser(existingOrder.getUser());
            order.setPaymentRef(existingOrder.getPaymentRef());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order createOrder(String email, Order order, String uuidPaymentRef) {
        User user = userRepository.findByEmail(email).orElse(null);
        Cart cart = cartRepository.findByUser(user).get();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentRef(order.getPaymentMethod().equals(PaymentMethod.COD) ? "UNKNOWN": uuidPaymentRef);
        order.setIsActive(true);
        // Tao order //
        Order saveOrder = orderRepository.save(order);
        // Tạo danh sách các orderDetail //
        orderDetailService.createOrderDetails(user,saveOrder,cart);
        cartRepository.deleteById(cart.getId());
        return saveOrder;
    }

    @Override
    public Order updatePaymentRef(String paymentRef, PaymentStatus status) {
        Optional<Order> orderOptional = orderRepository.findByPaymentRef(paymentRef);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setPaymentStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if(existingOrder != null) {
            existingOrder.setIsActive(false);
        }
    }

    @Override
    public int getTotalOrders() {
        return orderRepository.getTotalOrders();
    }

    @Override
    public List<Order> findTopOrdersByOrderDate(int pageNo, int pageSize, String orderDate) {
        Sort sort = Sort.by(Sort.Direction.DESC, orderDate);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<Order> orders = orderRepository.findAll(pageable).getContent();
        return orders;
    }


}
