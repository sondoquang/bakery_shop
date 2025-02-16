package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.*;
import com.stlang.bakery_shop.repositories.CartDetailRepository;
import com.stlang.bakery_shop.repositories.OrderDetailRepository;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import com.stlang.bakery_shop.services.iservices.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {

    private final ICartDetailService cartDetailService;
    private final OrderDetailRepository orderDetailRepository;
    private final CartDetailRepository cartDetailRepository;

    public OrderDetailService(ICartDetailService cartDetailService, OrderDetailRepository orderDetailRepository, CartDetailRepository cartDetailRepository) {
        this.cartDetailService = cartDetailService;
        this.orderDetailRepository = orderDetailRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public List<OrderDetail> createOrderDetails(User user, Order order, Cart cart) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<CartDetail> cartDetails = cart.getCartDetails();
        Double totalOfMoney = cartDetailService.getTotalAmount(cart.getId());
        for(CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .price(Double.parseDouble(cartDetail.getProduct().getPrice()+""))
                    .numberOfProducts(cartDetail.getQuantity())
                    .totalMoney(totalOfMoney)
                    .product(cartDetail.getProduct())
                    .order(order)
                    .build();
            orderDetailRepository.save(orderDetail);
            cartDetailRepository.deleteById(cartDetail.getId());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
