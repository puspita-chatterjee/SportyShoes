package com.SportyShoes.appConfig;

import com.SportyShoes.bean.ApplicationUser;
import com.SportyShoes.bean.OrderDetails;
import com.SportyShoes.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Application Data Access
 * Class.
 *
 */

@Repository
public class MongoDbConnector {

    @Qualifier("sportyShoeMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    public MongoDbConnector(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Method to get List
     * of Products.
     *
     * @return
     */
    public List<Product> getProducts(){
       List<Product> productList = null;
       Query isProductAvailable = new Query();
       isProductAvailable.addCriteria(Criteria.where("productForSale").is("Y"));
       productList = mongoTemplate.find(isProductAvailable, Product.class);
       return productList;
    }

    /**
     * Method validates user Login Inputs
     * w.r.t Database.
     *
     * @param userId
     * @param password
     * @return
     */
    public List<ApplicationUser> validateLogin(String userId, String password){
        List<ApplicationUser> userList = null;
        Query isUserAvailable = new Query();
        isUserAvailable.addCriteria(
                Criteria.where("username").is(userId).
                and("password").is(password));
        userList = mongoTemplate.find(isUserAvailable, ApplicationUser.class);

        return userList;
    }

    /**
     * Method fetches order details
     * from Mongo DB
     * @return
     */
    public List<OrderDetails> fetchOderList(){
        List<OrderDetails> orderDetails = null;
        orderDetails = mongoTemplate.findAll(OrderDetails.class);
        return orderDetails;
    }

    /**
     * Method to save Order details.
     *
     * @param orderDetails
     * @return
     */
    public OrderDetails saveCustomerOrder(OrderDetails orderDetails){
        return mongoTemplate.insert(orderDetails);
    }

    /**
     * Method to save New Product
     * details in Inventory.
     *
     * @param
     * @return
     */
    public Product addToInventory(Product productDetails){
        return mongoTemplate.insert(productDetails);
    }

    /**
     * Method to fetch Admin Profile
     * details.
     *
     * @param userProfile
     * @return
     */
    public ApplicationUser fetchProfile(ApplicationUser userProfile){
        List<ApplicationUser> userList = null;
        Query searchParam = new Query();
        searchParam.addCriteria(
                Criteria.where("adminFName").is(userProfile.getAdminFName().trim()).
                        and("adminLName").is(userProfile.getAdminLName().trim()));
        userList = mongoTemplate.find(searchParam, ApplicationUser.class);
        return userList.get(0);
    }

    /**
     * Method to update Admin Profile
     * details.
     * @param userProfile
     * @return
     */
    public ApplicationUser updateProfile(ApplicationUser userProfile){
        Query queryParam = new Query();
        queryParam.addCriteria(Criteria.where("adminId").is(userProfile.getAdminId()));
        Update update = new Update();
        update.set("role", userProfile.getRole());
        update.set("password", userProfile.getPassword());
        userProfile = mongoTemplate.findAndModify(queryParam, update, ApplicationUser.class);
        return userProfile;
    }
}