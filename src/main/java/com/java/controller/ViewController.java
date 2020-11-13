package com.java.controller;

import com.java.domain.HttpResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {

    @PreAuthorize("hasAuthority('admin:*')")
    @GetMapping("/one")
    public HttpResult one(){
        return new HttpResult(200,"success---one",null);
    }

    @PreAuthorize("hasAuthority('admin:add')")
    @GetMapping("/two")
    public HttpResult two(){
        return new HttpResult(200,"success---two",null);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping("/three")
    public HttpResult three(){
        return new HttpResult(200,"success---three",null);
    }



}
