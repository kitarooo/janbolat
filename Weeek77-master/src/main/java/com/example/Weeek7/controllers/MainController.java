package com.example.Weeek7.controllers;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.example.Weeek7.config.SwaggerConfig.MAIN;

@Api(tags = MAIN)
@RestController
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "Unsecured date";
    }

    @GetMapping("/secured")
    public String securedData(){
        return "Secured date";
    }

    @GetMapping("/info")
    public String userInfo(Principal principal){
        return principal.getName();
    }

}
