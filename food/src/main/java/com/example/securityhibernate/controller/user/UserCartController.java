package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.OrderItemDTO;
import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.CartService;
import com.example.securityhibernate.service.OrderService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/user/cart")
public class UserCartController {

    int idFood = 0;
    int idRes = 0;
    String getUserNameByToken = null;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @GetMapping("")
    public ResponseEntity<?> user() {
        return new ResponseEntity<>("user", HttpStatus.OK);
    }

    @PostMapping("/postIdFood")
    public ResponseEntity<?> addItemToCart(@RequestParam int idFoodByUser, @RequestParam int idResByUser, @RequestParam String token) {
        System.out.println(token + idFoodByUser);
        ResponseData responseData = new ResponseData();
        idFood = idFoodByUser;
        idRes = idResByUser;
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        if (idFood != 0) {
            responseData.setData(idFood);
            responseData.setDesc("Lấy thành công id food");
        } else {
            responseData.setDesc("Lấy thất bại id food");
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Get list foods
    @GetMapping("/getFoodById")
    public ResponseEntity<?> getFoodById() {
        ResponseData responseData = new ResponseData();
        if (idFood != 0) {
            responseData.setData(cartService.getListFoods(idFood, idRes, getUserNameByToken));
            responseData.setDesc("Lấy thành công thông tin food");
        } else {
            responseData.setData(cartService.getListFoods(idFood, idRes, getUserNameByToken));
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Create order item in cart
    @PostMapping("/postOrderItem")
    public ResponseEntity<?> postOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = orderService.saveOrder(getUserNameByToken,
                orderItemDTO.getIdFood(), orderItemDTO.getAmount(), orderItemDTO.getPrice());
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Lưu thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Lưu thât bại");
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Delete order item in cart
    @DeleteMapping("/deleteOrderItem/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("id") int id) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = cartService.deleteItemOder(id, getUserNameByToken);

        if (id == idFood) {
            idFood = 0;
        }

        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Xoá thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Xoá thất bại");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
