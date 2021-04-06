package com.SportyShoes.controller;


import com.SportyShoes.bean.Product;
import com.SportyShoes.service.SportyShoesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
@Slf4j
public class SportyShoesController {

    @Value("${spring.application.name}")
    String appName;

    @Autowired
    SportyShoesService sportyShoesService;

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

    @GetMapping("/product")
    public String productList(Model model) {
        List<Product> productList = sportyShoesService.getProductList();
        for(Product p: productList){
            log.info("Product Name - ", p.getProductName());
        }
        model.addAttribute("productList", productList);
        model.addAttribute("appName", appName);
        return "product";
    }
}