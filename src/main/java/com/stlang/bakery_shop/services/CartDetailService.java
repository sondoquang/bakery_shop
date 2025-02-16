package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.CartDetail;
import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.repositories.CartDetailRepository;
import com.stlang.bakery_shop.repositories.CartRepository;
import com.stlang.bakery_shop.repositories.ProductRepository;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailService implements ICartDetailService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartDetailService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public void removeItemFromCartDetail(long cartId, long productId) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        Cart existingCart = cartRepository.findById(cartId).orElse(null);
        Optional<CartDetail> cartDetail = cartDetailRepository.findByCartAndProduct(existingCart, existingProduct);
        if(existingProduct == null || existingCart == null) {
            throw new RuntimeException("Not found !!");
        }
        if (cartDetail.isPresent()) {
            cartDetailRepository.deleteById(cartDetail.get().getId());
            if (existingCart.getSumProduct() == 1) {
                cartRepository.deleteById(cartId);
            } else {
                existingCart.setSumProduct(existingCart.getSumProduct() - 1);
                cartRepository.save(existingCart);
            }
        }
    }

    @Override
    public CartDetail updateQuantity(long cartId, long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart,product).orElse(null);
        if(cartDetail != null){
            cartDetail.setQuantity(quantity);
            cartDetail.setTotalAmount(quantity * Double.parseDouble(product.getPrice()+""));
            return cartDetailRepository.save(cartDetail);
        }
        return null;
    }

    @Override
    public Double getTotalAmount(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        List<CartDetail> cartDetails = cart.getCartDetails();
        Double totalAmount = 0.0;
        for(CartDetail cartDetail : cartDetails){
            totalAmount += cartDetail.getTotalAmount();
        }
        return totalAmount;
    }
}
