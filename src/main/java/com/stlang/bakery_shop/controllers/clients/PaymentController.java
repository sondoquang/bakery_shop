package com.stlang.bakery_shop.controllers.clients;

import com.stlang.bakery_shop.domains.Cart;
import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.enums.PaymentMethod;
import com.stlang.bakery_shop.domains.enums.PaymentStatus;
import com.stlang.bakery_shop.services.OrderService;
import com.stlang.bakery_shop.services.VNpayService;
import com.stlang.bakery_shop.services.iservices.ICartDetailService;
import com.stlang.bakery_shop.services.iservices.ICartService;
import com.stlang.bakery_shop.services.iservices.IOrderService;
import com.stlang.bakery_shop.utils.XSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.UUID;


@Controller
public class PaymentController {

    private final IOrderService orderService;
    private final ICartService cartService;
    private final ICartDetailService cartDetailService;
    private final XSessionService xSessionService;
    private final VNpayService vnpayService;

    public PaymentController(OrderService orderService, ICartService cartService, ICartDetailService cartDetailService, XSessionService xSessionService, VNpayService vnpayService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
        this.xSessionService = xSessionService;
        this.vnpayService = vnpayService;
    }

    @RequestMapping("/thanks")
    public String thanks(Model model,
                         @RequestParam("vnp_TxnRef") Optional<String> paymentRef,
                         @RequestParam("vnp_ResponseCode") Optional<String> vnpayResponseCode) {
        if (vnpayResponseCode.isPresent() && paymentRef.isPresent()) {
            // check trạng thái thanh toán //
            PaymentStatus paymentStatus = vnpayResponseCode.get().equals("00") ? PaymentStatus.PAID : PaymentStatus.FAILED;
            orderService.updatePaymentRef(paymentRef.get(), paymentStatus);
        }
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
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        @RequestParam("totalAmount") Double totalAmount) {
        if (bindingResult.hasErrors()) {
            return "client/payment-view";
        }
        String email = xSessionService.getAttribute("email").toString();
        String uuidPaymentRef = UUID.randomUUID().toString().replace("-","");
        try {
            Order saveOrder = orderService.createOrder(email, order,uuidPaymentRef);
            if (!order.getPaymentMethod().equals(PaymentMethod.COD)) {
                // to do: redirect to vnPay
                String ip = vnpayService.getIpAddress(request);
                String vnpUrl = vnpayService.generateVNPayURL(totalAmount, uuidPaymentRef, ip);
                return "redirect:" + vnpUrl;
            }
            return "redirect:/thanks";
        } catch (Exception e) {
            return "client/payment-view";
        }
    }

}
