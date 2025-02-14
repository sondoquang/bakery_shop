package com.stlang.bakery_shop.controllers.clients;


import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.services.iservices.ICartService;
import com.stlang.bakery_shop.utils.XSessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class CartRequest {
    private long quantity;
    private long productId;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}

@RestController
public class CartAPI {

    @Autowired
    XSessionService session;

    private final ICartService cartService;

    public CartAPI(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/api/v1/product/add")
    public ResponseEntity<Integer> addProductToCart(@RequestBody CartRequest cartRequest,
                                                    HttpServletRequest request) {
        String email = session.getAttribute("email");
        cartRequest.setQuantity(cartRequest.getQuantity() <= 0 ? 1 : cartRequest.getQuantity());
        Cart cart = cartService.addProductToCart(email, cartRequest.getProductId(), (int) cartRequest.getQuantity());
        request.getSession().setAttribute("sumProductCart", cart.getSumProduct());
        return ResponseEntity.ok().body(cart.getSumProduct());
    }
}
