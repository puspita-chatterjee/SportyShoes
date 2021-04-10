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
import java.util.ListIterator;
import java.util.UUID;

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
    public boolean saveCustomerOrder(OrderDetails orderDetails){
        boolean isSaved = false;
        String confirmationNumber = "SS-"+ UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        orderDetails.setOrderId(confirmationNumber);
        orderDetails.setOrderStatus("In Progress");
        orderDetails.setOrderDate(new Date().toString());
        orderDetails.setOrderCreateDate(new Date().toString());
        log.info("orderDetails- ", orderDetails);
        orderDetails = mongoDbConnector.saveCustomerOrder(orderDetails);
        if(orderDetails != null)
            isSaved = true;
        return isSaved;
    }
}