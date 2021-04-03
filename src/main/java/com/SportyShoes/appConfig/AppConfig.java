package com.SportyShoes.appConfig;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.port}")
    String port;

    @Value("${spring.datasource.db}")
    String db;

    /*
     * Use the standard Mongo driver API to
     * create a com.mongodb.client.MongoClient instance.
     */
    @Bean("sportyShoeMongoClient")
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://"+url+":"+port);
    }

    @Bean("sportyShoeMongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), db);
    }
}