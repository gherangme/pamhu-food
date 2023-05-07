package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.CartService;
import com.example.securityhibernate.service.CheckoutService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/checkout")
public class UserCheckoutController {

    String getUserNameByToken = null;
    double totalPrice = 0;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private CartService cartService;

    @PostMapping("/postInforCheckout")
    public ResponseEntity<?> postInforCheckout(@RequestParam String token, @RequestParam double price) {
        ResponseData responseData = new ResponseData();
        System.out.println(token);
        if (token != null) {
            getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
            totalPrice = price;
            responseData.setData(getUserNameByToken);
        } else {
            responseData.setDesc("post thất bại infor checkout");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getInforCheckout")
    public ResponseEntity<?> getInforCheckout() {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        responseData.setData(checkoutService.getUserByUsername(getUserNameByToken));
        responseData.setDesc("Lấy thành công thông tin user");
        list.add(responseData);

        ResponseData responseData1 = new ResponseData();
        responseData1.setData(cartService.getListFoods(0, getUserNameByToken));
        responseData1.setDesc(Double.toString(totalPrice));
        list.add(responseData1);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
