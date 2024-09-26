package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.OrderService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/orders")
public class UserOrderController {

    private String token = null;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @GetMapping("/{tokenRequest}")
    public ResponseEntity<?> getOrder(@PathVariable String tokenRequest) {
        System.out.println(tokenRequest);
        if (tokenRequest != null) {
            token = tokenRequest;
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        System.out.println(token);
        return new ResponseEntity<>(new ResponseData(orderService.
                getAllOrdersByIdUser(jwtUtilsHelpers.getIdUserByToken(token))), HttpStatus.OK);
    }

}
