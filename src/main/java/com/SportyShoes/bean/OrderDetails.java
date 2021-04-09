package com.SportyShoes.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderdetails")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class OrderDetails {
    String orderId;
    String orderStatus;
    String customerName;
    String orderQty;
    String orderProduct;
    String orderDate;
    String orderCreateDate;
}
