package com.example.securityhibernate.controller.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {

    @GetMapping("")
    public String domain() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/restaurant")
    public String restaurant() {
        return "restaurant";
    }

    @GetMapping("/403")
    public String notAuthor() {
        return "403";
    }

    @GetMapping("/401")
    public String notAuthen() {
        return "401";
    }

    @GetMapping("/food")
    public String food() {
        return "food";
    }

    @GetMapping("/restaurant-detail")
    public String restaurantDetail() {
        return "restaurant-detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/otp")
    public String otp() {
        return "otp";
    }

    @GetMapping("/forgot")
    public String forgot() {
        return "forgot";
    }

    @GetMapping("/forgot-otp")
    public String forgotOTP() {
        return "forgot-otp";
    }

    @GetMapping("/food-detail")
    public String ratingFood() {
        return "food-detail";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/manager-food-detail")
    public String managerFoodDetail() {
        return "manager-food-detail";
    }

    @GetMapping("/manager-food-add")
    public String managerFoodAdd() {
        return "manager-food-add";
    }

    @GetMapping("/manager-invoice-detail")
    public String invoice() {
        return "manager-invoice-detail";
    }

    @GetMapping("/manager-promotion-detail")
    public String managerPromotion() {
        return "manager-promotion-detail";
    }

}
