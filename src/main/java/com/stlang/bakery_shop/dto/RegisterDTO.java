package com.stlang.bakery_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private String password;
    private String confirmPassword;
}
