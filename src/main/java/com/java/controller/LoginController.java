package com.java.controller;

import com.java.domain.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public HttpResult login(){
        return new HttpResult(403,"please login",null);
    }

}
