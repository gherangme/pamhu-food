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

    private int idFood = 0;
    private int idRes = 0;
    private String getUserNameByToken = null;

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
    public ResponseEntity<?> postIdFood(@RequestParam int idFoodByUser,
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

    @PostMapping("/postCheckCart/{token}")
    public ResponseEntity<?> postCheckCart(@PathVariable String token) {
        System.out.println("test");
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        idFood = 0;
        return new ResponseEntity<>(new ResponseData(true,
                "Post check cart thành công"), HttpStatus.OK);
    }

    // Get list foods when add cart
    @GetMapping("/getFoodById/{token}")
    public ResponseEntity<?> getFoodById(@PathVariable String token) {
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        if (token != null) {
            if (idFood != 0) {
                return new ResponseEntity<>(new ResponseData(cartService.addFoodToCart(idFood, idRes, getUserNameByToken),
                        "Lấy thành công thông tin food"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseData(cartService.addFoodToCart(idFood, idRes, getUserNameByToken),
                        "Lấy thành công thông tin food",
                        404), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ResponseData("Lấy thất bại thông tin food",
                    400), HttpStatus.OK);
        }
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
