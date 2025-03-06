package com.stlang.bakery_shop.controllers.admins;

import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.domains.enums.OrderStatus;
import com.stlang.bakery_shop.domains.enums.ShippingMethod;
import com.stlang.bakery_shop.services.iservices.IOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderAController {

    String errors [] = {"", "Order not found !", "Action failed !!"};

    private final IOrderService orderService;

    public OrderAController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @RequestMapping({"/admin/order/index","/admin/order/index/{errorIds}" })
    public String getFormOrder(Model model,
                               @PathVariable Optional<Integer> status,
                               HttpSession session) {
        session.setAttribute("active", 2);

        if(!model.asMap().containsKey("order")){
            model.addAttribute("order", new Order());
        }

        List<Order> orders = orderService.findByIsActive(true);
        model.addAttribute("orders", orders);
        model.addAttribute("shippingMethods", ShippingMethod.values());
        model.addAttribute("ordersStatus", OrderStatus.values());
        model.addAttribute("msg", errors[status.orElse(0)]);
        return "admin/order-view";
    }

    @RequestMapping("/admin/order/edit/{orderId}")
    public String formOrderEdit( @PathVariable long orderId, RedirectAttributes redirectAttributes) {
        Order order = orderService.findById(orderId);
        if(order != null) {
            redirectAttributes.addFlashAttribute("order", order);
            return "redirect:/admin/order/index";
        }
        return "redirect:/admin/order/index/1";
    }

    @RequestMapping("/admin/order/delete/{orderId}")
    public String formOrderDelete(@PathVariable long orderId) {
        orderService.delete(orderId);
        return "redirect:/admin/order/index";
    }


    @RequestMapping("/admin/order/update")
    public String formOrderUpdate(@ModelAttribute("order") Order order, RedirectAttributes redirectAttributes) {
        Order updateOrder = orderService.update(order);
        if(updateOrder != null) {
            redirectAttributes.addFlashAttribute("order", updateOrder);
            return "redirect:/admin/order/index/0";
        }
        return "redirect:/admin/order/index/2";
    }

}
