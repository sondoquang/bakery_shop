package com.stlang.bakery_shop.services;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.CartDetail;
import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.repositories.CartDetailRepository;
import com.stlang.bakery_shop.repositories.CartRepository;
import com.stlang.bakery_shop.repositories.ProductRepository;
import com.stlang.bakery_shop.repositories.UserRepository;
import com.stlang.bakery_shop.services.iservices.ICartService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public CartService(CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addProductToCart(String email, long productId, int quantity) {
        User user = userRepository.findByEmail(email).orElse(null);
        Cart cart = null;
        if (user != null) {
            /* Check valid user */
            cart = cartRepository.findByUser(user).orElse(null);
            if (cart == null) {
                /* if cart not valid --> create cart for user */
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSumProduct(0);
                cart = cartRepository.save(newCart);
            }
            // find product by id //
            Optional<Product> oProduct = productRepository.findById(productId);
            if (oProduct.isPresent()) {
                Product product = oProduct.get();
                CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, product).orElse(null);
                if (cartDetail == null) {
                    cart.setSumProduct(cart.getSumProduct() + 1);
                    cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(product);
                    cartDetail.setTotalAmount(Double.parseDouble(product.getPrice()+""));
                    cartDetail.setQuantity(quantity);
                }else{
                    if(quantity > 1){
                        cartDetail.setQuantity(quantity);
                    }else{
                        cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                    }
                    cartDetail.setTotalAmount(Double.parseDouble(product.getPrice() + "") * quantity);
                }
                cartDetailRepository.save(cartDetail);
            }
        }
        return cart;
    }

    @Override
    public Cart findByUser(String email) {
        User existUser = userRepository.findByEmail(email).orElse(null);
        if(existUser != null) {
            return cartRepository.findByUser(existUser).orElse(null);
        }
        return null;
    }
}
