package com.stlang.bakery_shop.controllers.admins;


import com.stlang.bakery_shop.domains.Order;
import com.stlang.bakery_shop.services.iservices.IOrderService;
import com.stlang.bakery_shop.services.iservices.IProductService;
import com.stlang.bakery_shop.services.iservices.IUserService;
import com.stlang.bakery_shop.utils.XSessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final IUserService userService;
    private final IProductService productService;
    private final IOrderService orderService;


    public DashboardController(IUserService userService, IProductService productService, IOrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @RequestMapping("/admin/dashboard")
    public String dashboard(Model model, HttpSession session) {
        session.setAttribute("active", 0);

        List<Order> orders = orderService.findTopOrdersByOrderDate(0,10,"orderDate");
        model.addAttribute("orders",orders);
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("totalOrders", orderService.getTotalOrders());
        return "admin/dashboard-view";
    }
}
