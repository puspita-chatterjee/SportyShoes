package com.SportyShoes.service;

import com.SportyShoes.appConfig.MongoDbConnector;
import com.SportyShoes.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
}