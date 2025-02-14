package com.stlang.bakery_shop.controllers.auth;

import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.dto.RegisterDTO;
import com.stlang.bakery_shop.services.iservices.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;

    public RegisterController(PasswordEncoder passwordEncoder, IUserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("registerUser") RegisterDTO registerUser) {
        return "client/register-view";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerUser") RegisterDTO registerUser, Model model) {
        User user = userService.registerDTOToUser(registerUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userService.addUser(user);
        if(saveUser != null) {
            return "redirect:/login";
        }
        model.addAttribute("msg", "Register unsuccessful !");
        return "client/register-view";
    }
}
