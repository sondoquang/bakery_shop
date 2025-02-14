package com.stlang.bakery_shop.controllers.clients;


import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.CartDetail;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import com.stlang.bakery_shop.services.iservices.ICartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final ICartService cartService;
    private final ICartDetailService cartDetailService;


    public CartController(ICartService cartService, ICartDetailService cartDetailService) {
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
    }
    
    @GetMapping("my-cart")
    public String myCart(HttpSession session, Model model) {

        Cart cart = cartService.findByUser(session.getAttribute("email").toString());
        if(cart != null) {
            model.addAttribute("products", cart.getCartDetails());
        }else{
            model.addAttribute("products", new ArrayList<CartDetail>());
        }
        model.addAttribute("cart", cart);
        return "client/cart-view";
    }

    @RequestMapping ("/my-cart/remove/{productId}")
    public String deleteCart(@PathVariable int productId, HttpSession session) {
        String emailUser = session.getAttribute("email").toString();
        if(emailUser != null) {
            Cart cart = cartService.findByUser(emailUser);
            if(cart != null) {
                cartDetailService.removeItemFromCartDetail(cart.getId(),productId);
            }
        }
        return "redirect:/my-cart";
    }

    @RequestMapping("/my-cart/update/{productId}")
    public String updateCart(@PathVariable int productId,
                             HttpSession session,
                             @RequestParam("quantity") int qty) {
        String emailUser = session.getAttribute("email").toString();
        if(emailUser != null) {
            Cart cart = cartService.findByUser(emailUser);
            if(cart != null) {
                cartDetailService.updateQuantity(cart.getId(),productId,qty);
            }
        }
        return "redirect:/my-cart";
    }

}
