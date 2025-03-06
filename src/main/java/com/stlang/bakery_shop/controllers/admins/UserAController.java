package com.stlang.bakery_shop.controllers.admins;

import com.stlang.bakery_shop.domains.Product;
import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.services.iservices.IUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class UserAController {

    private final String[] errors =
            {
                    "",
                    "Data not found !",
                    "Update user failed !",
                    "Email already exists !",
                    "Phone number already exists !",
                    "Can't delete yourself !"
            };
    private final IUserService userService;

    public UserAController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping({"/admin/account/index","/admin/account/index/{error}"})
    public String formUser(Model model,
                           @PathVariable("error") Optional<Integer> error,
                           HttpSession session) {
        session.setAttribute("active", 1);
        // Nếu có lỗi //
        model.addAttribute("msg",errors[error.orElse(0)]);
        if(model.asMap().containsKey("user")) {
            model.addAttribute("user", model.asMap().get("user"));
        }else
            model.addAttribute("user", new User());

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "admin/account-view";
    }

    @PostMapping("/admin/account/update")
    public String updateUser(@ModelAttribute("account") @Valid User user,
                             RedirectAttributes redirectAttributes,
                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin/account/index";
        }
        int result = userService.updateUser(user);
        return switch (result) {
            case -1 -> "redirect:/admin/account/index/1";
            case -2 -> "redirect:/admin/account/index/3";
            case -3 -> "redirect:/admin/account/index/4";
            default -> "redirect:/admin/account/index";
        };
    }

    @RequestMapping("/admin/account/edit/{userId}")
    public String edit(@PathVariable int userId,
                       RedirectAttributes redirectAttributes) {
        User user = userService.findById(userId);
        if(user == null){
            return "redirect:/admin/account/index/1";
        }
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/admin/account/index";
    }

    @RequestMapping("/admin/account/create")
    public String createUser(@ModelAttribute("account") @Valid User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin/account/index";
        }
        int result = userService.createUser(user);
        return switch (result) {
            case -2 -> "redirect:/admin/account/index/3";
            case -3 -> "redirect:/admin/account/index/4";
            default -> "redirect:/admin/account/index";
        };
    }

    @RequestMapping("/admin/account/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId, HttpSession session) {

        int result = userService.deleteUser(userId, session.getAttribute("fullName").toString());
        if(result == -1){
            return "redirect:/admin/account/index/1";
        }
        if(result == -2){
            return "redirect:/admin/account/index/5";
        }
        return "redirect:/admin/account/index";
    }





}
