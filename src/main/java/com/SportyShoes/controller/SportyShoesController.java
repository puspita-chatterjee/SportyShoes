package com.SportyShoes.controller;


import com.SportyShoes.bean.ApplicationUser;
import com.SportyShoes.bean.OrderDetails;
import com.SportyShoes.bean.Product;
import com.SportyShoes.service.SportyShoesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * API Controller class -
 * which Server all Incoming
 * requests.
 */

@Controller
@Slf4j
public class SportyShoesController {

    @Value("${spring.application.name}")
    String appName;

    @Autowired
    SportyShoesService sportyShoesService;

    /**
     * Application Home Page.
     *
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",
                            required=false,
                            defaultValue="Customer") String name,
                            Model model) {
        model.addAttribute("name", name);
        model.addAttribute("appName", appName);
        return "greeting";
    }

    /**
     * Handles API Login feature.
     *
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam(name="name",
                                    required=false,
                                    defaultValue="Customer") String name,
                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("appName", appName);
        return "login";
    }

    /**
     * API Login validation.
     *
     * @param request
     * @param model
     * @return
     */
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

    /**
     * Handles API ordering feature.
     *
     * @param model
     * @return
     */
    @GetMapping("/OrderShoe")
    public String OrderShoe(Model model){
        model.addAttribute("appName", appName);
        return "orderShoe";
    }

    /**
     * API Ordering and Save into DB
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/placeOrder")
    public String placeOrder(HttpServletRequest request, Model model){
        Map<String, String[]> requestParam = request.getParameterMap();
        String customerName[] = requestParam.get("customerName");
        String orderQuantity[] = requestParam.get("orderQty");
        String productTypes[] = requestParam.get("productType");
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomerName(customerName[0]);
        orderDetails.setOrderQty(orderQuantity[0]);
        orderDetails.setOrderProduct(productTypes[0]);
        String orderConfirmation = sportyShoesService.saveCustomerOrder(orderDetails);

        model.addAttribute("appName", appName);
        model.addAttribute("orderConfirmation", orderConfirmation);

        return "orderPlaceSuccess";
    }

    /**
     * Handles API Product Fetch.
     *
     * @param model
     * @return
     */
    @GetMapping("/product")
    public String productList(Model model) {
        List<Product> productList = sportyShoesService.getProductList();
        model.addAttribute("productList", productList);
        model.addAttribute("appName", appName);
        return "product";
    }

    /**
     * Handles API Order Fetch.
     *
     * @param model
     * @return
     */
    @GetMapping("/checkOrder")
    public String fetchOderList(Model model){
        List<OrderDetails> orderList = sportyShoesService.fetchOderList();
        model.addAttribute("orderList", orderList);
        model.addAttribute("appName", appName);
        return "fetchOrderDetails";
    }

    /**
     * Handles API Addition.
     *
     * @param model
     * @return
     */
    @GetMapping("/addProduct")
    public String addProduct(Model model){
        return "addProduct";
    }

    /**
     * API Product addition into DB.
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/addToInventory")
    public String addToInventory(HttpServletRequest request, Model model){
        Map<String, String[]> productRequestParam = request.getParameterMap();
        String productId[] = productRequestParam.get("productId");
        String productName[] = productRequestParam.get("productName");
        String productManufacturer[] = productRequestParam.get("productManufacturer");
        String productSize[] = productRequestParam.get("productSize");
        String productFor[] = productRequestParam.get("productFor");
        Product productDetails = new Product();
        productDetails.setProductId(productId[0]);
        productDetails.setProductName(productName[0]);
        productDetails.setProductManufacturer(productManufacturer[0]);
        productDetails.setProductSize(productSize[0]);
        productDetails.setProductFor(productFor[0]);
        String addedProductName = sportyShoesService.addToInventory(productDetails);

        model.addAttribute("appName", appName);
        model.addAttribute("addedProductName", addedProductName);
        return "productAdded";
    }

    /**
     * Handles API Deletion.
     *
     * @param model
     * @return
     */
    @GetMapping("/removeProduct")
    public String removeProduct(Model model){
        List<Product> productList = sportyShoesService.getProductList();
        model.addAttribute("productList", productList);
        model.addAttribute("appName", appName);
        return "removeProduct";
    }

    /**
     * Handles API
     * Admin Profile Fetch.
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/fetchProfile")
    public String fetchProfile(HttpServletRequest request,Model model){
        Map<String, String[]> userNameParam = request.getParameterMap();
        String userName[] = userNameParam.get("userName");
        String searchParam = userName[0];
        ApplicationUser userProfileDetails = sportyShoesService.fetchProfile(searchParam);
        model.addAttribute("adminId", userProfileDetails.getAdminId());
        model.addAttribute("adminFName", userProfileDetails.getAdminFName());
        model.addAttribute("adminLName", userProfileDetails.getAdminLName());
        model.addAttribute("role", userProfileDetails.getRole());
        model.addAttribute("username", userProfileDetails.getUsername());
        model.addAttribute("password", userProfileDetails.getPassword());
        model.addAttribute("email", userProfileDetails.getEmail());
        model.addAttribute("phone", userProfileDetails.getPhone());
        model.addAttribute("appName", appName);
        return "profileDetails";
    }

    /**
     * Handles API
     * Admin Profile Updates.
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/updateAccount")
    public String updateProfile(HttpServletRequest request,Model model){
        Map<String, String[]> userUpdateParam = request.getParameterMap();

        String adminId[] = userUpdateParam.get("adminId");
        String role[] = userUpdateParam.get("role");
        String password[] = userUpdateParam.get("password");
        String adminEmail[] = userUpdateParam.get("adminEmail");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setAdminId(adminId[0]);
        applicationUser.setRole(role[0]);
        applicationUser.setPassword(password[0]);
        applicationUser.setEmail(adminEmail[0]);

        sportyShoesService.updateProfile(applicationUser);

        return "profileUpdateSuccess";
    }

    @GetMapping("/createUser")
    public String createUser(Model model){
        return "createUser";
    }

    @GetMapping("/registerUser")
    public String registerUser(HttpServletRequest request,Model model){
        Map<String, String[]> userCreateParam = request.getParameterMap();

        String userFName[] = userCreateParam.get("userFName");
        String userLName[] = userCreateParam.get("userLName");
        String userGender[] = userCreateParam.get("userGender");
        String userRole[] = userCreateParam.get("userRole");
        String userLoginName[] = userCreateParam.get("userLoginName");
        String userLoginPassword[] = userCreateParam.get("userLoginPassword");
        String userEmail[] = userCreateParam.get("userEmail");
        String userPhone[] = userCreateParam.get("userPhone");

        ApplicationUser createApplicationUser = new ApplicationUser();
        createApplicationUser.setAdminFName(userFName[0]);
        createApplicationUser.setAdminLName(userLName[0]);
        createApplicationUser.setGender(userGender[0]);
        createApplicationUser.setRole(userRole[0]);
        createApplicationUser.setUsername(userLoginName[0]);
        createApplicationUser.setPassword(userLoginPassword[0]);
        createApplicationUser.setEmail(userEmail[0]);
        createApplicationUser.setPhone(userPhone[0]);

        sportyShoesService.createUserProfile(createApplicationUser);

        return "profileCreationSuccess";
    }
}