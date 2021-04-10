package com.SportyShoes.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * API Bean
 */

@Document(collection = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApplicationUser {
    private String adminId;
    private String adminFName;
    private String adminLName;
    private String role;
    private String active;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String gender;
}