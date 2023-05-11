package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.security.CustomUserDetails;
import com.example.securityhibernate.security.CustomUserDetailsSerializer;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username,
                                    @RequestParam String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Fix Gson does not know how to serialize this object by default (Principal, Credentials, Authen, Details, Grant)
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Pick up principal
        gsonBuilder.registerTypeAdapter(CustomUserDetails.class, new CustomUserDetailsSerializer());
        Gson gson = gsonBuilder.create();

        String data = gson.toJson(authentication.getPrincipal());

        ResponseData responseData = new ResponseData();
        responseData.setData(jwtUtilsHelpers.generateToken(data, username, loginService.getIdUserByUsername(username)));
        responseData.setDesc("Đăng nhập thành công");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/getInforUserByToken")
    public ResponseEntity<?> getInforUserByToken(@RequestParam String token) {
        ResponseData responseData = new ResponseData();

        if (!loginService.getFullNameByToken(token).equals("Guess")) {
            responseData.setData(loginService.getFullNameByToken(token));
        } else {
            responseData.setData(loginService.getFullNameByToken(token));
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}