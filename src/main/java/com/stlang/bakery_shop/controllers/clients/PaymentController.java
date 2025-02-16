package com.stlang.bakery_shop.controllers.clients;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.services.OrderService;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import com.stlang.bakery_shop.services.iservices.ICartService;
import com.stlang.bakery_shop.services.iservices.IOrderService;
import com.stlang.bakery_shop.utils.XSessionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PaymentController {

    private final IOrderService orderService;
    private final ICartService cartService;
    private final ICartDetailService cartDetailService;
    private final XSessionService xSessionService;

    public PaymentController(OrderService orderService, ICartService cartService, ICartDetailService cartDetailService, XSessionService xSessionService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
        this.xSessionService = xSessionService;
    }

    @RequestMapping("/thanks")
    public String thanks() {
        return "client/thank-view";
    }

    @RequestMapping("/payment")
    public String payment(@ModelAttribute("order") Order order, Model model) {
        order.setEmail(xSessionService.getAttribute("email"));
        order.setShippingAddress(xSessionService.getAttribute("address"));
        order.setFullname(xSessionService.getAttribute("fullName"));
        order.setPhoneNumber(xSessionService.getAttribute("phoneNumber"));


        Cart cart = cartService.findByUser(xSessionService.getAttribute("email"));
        Double totalAmount = cartDetailService.getTotalAmount(cart.getId());
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalProduct", cart.getSumProduct());
        return "client/payment-view";
    }

    @RequestMapping("/payment/check")
    public String check(@Valid @ModelAttribute("order") Order order,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/payment-view";
        }
        String email = xSessionService.getAttribute("email").toString();
        try {
            Order saveOrder = orderService.createOrder(email, order);
            return "redirect:/thanks";
        } catch (Exception e) {
            return "client/payment-view";
        }
    }

}
