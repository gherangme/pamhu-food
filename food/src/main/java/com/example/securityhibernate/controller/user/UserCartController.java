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

    // Post id food
    @PostMapping("/postIdFood")
    public ResponseEntity<?> addItemToCart(@RequestParam int idFoodByUser,
                                           @RequestParam int idResByUser,
                                           @RequestParam String token) {
        idFood = idFoodByUser;
        idRes = idResByUser;
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        System.out.println(idFoodByUser + idResByUser + token);

        if (idFood != 0) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thành công id food"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Lấy thất bại id food",
                    400), HttpStatus.OK);
        }
    }

    // Get list foods
    @GetMapping("/getFoodById")
    public ResponseEntity<?> getFoodById() {
//        ResponseData responseData = new ResponseData();
//        if (idFood != 0) {
//            responseData.setData(cartService.getListFoods(idFood, idRes, getUserNameByToken));
//            responseData.setDesc("Lấy thành công thông tin food");
//        } else {
//            responseData.setData(cartService.getListFoods(idFood, idRes, getUserNameByToken));
//        }
        return new ResponseEntity<>(new ResponseData(cartService.getListFoods(idFood, idRes, getUserNameByToken),
                "Lấy thành công thông tin food"), HttpStatus.OK);
    }

    // Create order item in cart
    @PostMapping("/postOrderItem")
    public ResponseEntity<?> postOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        boolean isSuccess = orderService.saveOrder(getUserNameByToken,
                orderItemDTO.getIdFood(), orderItemDTO.getAmount(), orderItemDTO.getPrice());

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Lưu thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Lưu thât bại",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete order item in cart
    @DeleteMapping("/deleteOrderItem/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("id") int id) {
        boolean isSuccess = cartService.deleteItemOder(id, getUserNameByToken);

        if (id == idFood) {
            idFood = 0;
        }

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Xoá thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Xoá thành công",
                    400), HttpStatus.BAD_REQUEST);
        }
    }
}
