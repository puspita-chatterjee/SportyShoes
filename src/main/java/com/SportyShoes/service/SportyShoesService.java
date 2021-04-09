package com.SportyShoes.service;

import com.SportyShoes.appConfig.MongoDbConnector;
import com.SportyShoes.bean.ApplicationUser;
import com.SportyShoes.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
}