package com.SportyShoes.service;

import com.SportyShoes.appConfig.MongoDbConnector;
import com.SportyShoes.bean.ApplicationUser;
import com.SportyShoes.bean.OrderDetails;
import com.SportyShoes.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Application Service class
 * to perform business Logic.
 *
 */

@Component
@Slf4j
public class SportyShoesService {

    @Autowired
    MongoDbConnector mongoDbConnector;

    /**
     * Method to get List
     * of Products.
     *
     * @return
     */
    public List<Product> getProductList(){
        List<Product> productList = mongoDbConnector.getProducts();
        return productList;
    }

    /**
     * This method validates
     * user credentials in Mongo.
     *
     * @param userId
     * @param password
     * @return
     */
    public List<ApplicationUser> validateUserCredentials(String userId, String password){
        List<ApplicationUser> userList = null;
        userList = mongoDbConnector.validateLogin(userId, password);

        return userList;
    }

    /**
     * Method to return
     * Order details.
     *
     * @return
     */
    public List<OrderDetails> fetchOderList(){
        List<OrderDetails> orderDetails = null;
        orderDetails = mongoDbConnector.fetchOderList();
        return orderDetails;
    }

    /**
     * Method to save Order details
     * in Mongo.
     *
     * @param orderDetails
     * @return
     */
    public String saveCustomerOrder(OrderDetails orderDetails){
        String confirmationNumber = "SS-"+ UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        orderDetails.setOrderId(confirmationNumber);
        orderDetails.setOrderStatus("In Progress");
        orderDetails.setOrderDate(new Date().toString());
        orderDetails.setOrderCreateDate(new Date().toString());
        log.info("orderDetails- ", orderDetails);
        orderDetails = mongoDbConnector.saveCustomerOrder(orderDetails);
        return orderDetails.getOrderId();
    }

    /**
     * Method responsible to save
     * Product details in Mongo.
     *
     * @param productDetails
     * @return
     */
    public String addToInventory(Product productDetails){
        productDetails.setProductForSale("Y");
        productDetails = mongoDbConnector.addToInventory(productDetails);
        return productDetails.getProductName();
    }

    /**
     * Method to fetch User details.
     *
     * @param searchParam
     * @return
     */
    public ApplicationUser fetchProfile(String searchParam){
        ApplicationUser userProfile = new ApplicationUser();
        System.out.println("Query Param - "+searchParam);
        userProfile.setAdminFName(searchParam.substring(0,searchParam.indexOf(' ')));
        userProfile.setAdminLName(searchParam.substring(searchParam.indexOf(' '), searchParam.length()));
        userProfile = mongoDbConnector.fetchProfile(userProfile);
        return userProfile;
    }

    /**
     * Method to update User details.
     *
     * @param applicationUser
     * @return
     */
    public boolean updateProfile(ApplicationUser applicationUser){
        boolean isUpdated = false;
        applicationUser = mongoDbConnector.updateProfile(applicationUser);
        if(applicationUser != null)
            isUpdated = true;
        return isUpdated;
    }

    /**
     * Method to create User.
     *
     * @param applicationUser
     * @return
     */
    public ApplicationUser createUserProfile(ApplicationUser applicationUser){
        int min = 105;
        int max = 200;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        applicationUser.setAdminId(Integer.toString(random_int));
        applicationUser.setActive("Y");
        applicationUser = mongoDbConnector.createUserProfile(applicationUser);
        return applicationUser;
    }
}