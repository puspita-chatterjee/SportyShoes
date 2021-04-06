package com.SportyShoes.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SportyShoesController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",
                            required=false,
                            defaultValue="Customer") String name,
                            Model model) {
        model.addAttribute("name", name);
        model.addAttribute("appName", appName);
        return "greeting";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="name",
            required=false,
            defaultValue="Customer") String name,
                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("appName", appName);
        return "login";
    }
}