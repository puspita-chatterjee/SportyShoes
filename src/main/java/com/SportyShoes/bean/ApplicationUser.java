package com.SportyShoes.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApplicationUser {
    String adminId;
    String adminFName;
    String adminLName;
    String role;
    String active;
    String username;
    String password;
}