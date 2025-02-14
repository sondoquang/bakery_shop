package com.stlang.bakery_shop.controllers.clients;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHome() {
        return "client/home-view";
    }
}
