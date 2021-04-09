package com.SportyShoes.controller;


import com.SportyShoes.bean.ApplicationUser;
import com.SportyShoes.bean.Product;
import com.SportyShoes.service.SportyShoesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @GetMapping ("/validateLogin")
    public String validateLogin(HttpServletRequest request, Model model) {
        String redirectUrl = "";
        List<ApplicationUser> userList = null;
        Map<String, String[]> requestParam = request.getParameterMap();
        String uname[] = requestParam.get("uname");
        String pass[] = requestParam.get("pass");
        String userId = uname[0];
        String password = pass[0];

        userList = sportyShoesService.validateUserCredentials(userId, password);
        if(userList.size() > 0
                && userList.get(0).getRole().equalsIgnoreCase("Store Manager")) {
            redirectUrl = "adminLoginSuccess";
            model.addAttribute("name",
                    userList.get(0).getAdminFName() + " "+userList.get(0).getAdminLName());
        }
        else if(userList.size() > 0 &&
                userList.get(0).getRole().equalsIgnoreCase("Store Customer")) {
            redirectUrl = "CustomerLoginSuccess";
            model.addAttribute("name",
                    userList.get(0).getAdminFName() + " "+userList.get(0).getAdminLName());
        }
        else
            redirectUrl = "loginRetry";
        model.addAttribute("appName", appName);
        return redirectUrl;
    }

    @GetMapping("OrderShoe")
    public String OrderShoe(Model model){
        model.addAttribute("appName", appName);
        return "orderShoe";
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