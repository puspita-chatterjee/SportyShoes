package com.SportyShoes.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shoeList")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Product {
    private String productId;
    private String productName;
    private String productManufacturer;
    private String productSize;
    private String productFor;
    private String productForSale;
}