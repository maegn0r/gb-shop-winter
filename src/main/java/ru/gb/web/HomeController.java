package ru.gb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String getRootPath() {
        return "redirect:/product/all";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "errors/access-denied";
    }

    @GetMapping("/not-found")
    public String showNotFound(){
        return "errors/not-found";
    }
}
