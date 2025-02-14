package com.stlang.bakery_shop.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDenied {

    @GetMapping("/access-deny")
    public String accessDenied() {
        return "client/access-denied";
    }
}
