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

    @GetMapping("/food")
    public String food() {
        return "/food";
    }

    @GetMapping("/restaurant-detail")
    public String restaurantDetail() {
        return "/restaurant-detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "/checkout";
    }

}
