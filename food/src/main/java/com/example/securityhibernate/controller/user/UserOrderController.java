package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.OrderService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/order")
public class UserOrderController {

    private String token = null;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @GetMapping("/getTokenUser/{tokenByUser}")
    public ResponseEntity<?> postTokenUser(@PathVariable String tokenByUser) {
        System.out.println(tokenByUser);
        if (tokenByUser != null) {
            token = tokenByUser;
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thành công thông tin token"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Lấy thất bại thông tin token",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllOrderByIdUser")
    public ResponseEntity<?> getAllOrderByIdUser() {
        System.out.println(token);
        return new ResponseEntity<>(new ResponseData(orderService.getAllOrderByIdUser(jwtUtilsHelpers.getIdUserByToken(token)),
                "Lấy thành công danh sách order"), HttpStatus.OK);
    }

}
