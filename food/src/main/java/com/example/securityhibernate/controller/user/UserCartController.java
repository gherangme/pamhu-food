package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.response.OrderItemDTO;
import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.CartService;
import com.example.securityhibernate.service.OrderService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCartController {

    int idFood = 0;
    int idRes = 0;
    String getUserNameByToken = null;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    @PostMapping("/{idFoodRequest}")
    public ResponseEntity<?> getIdFood(@RequestParam int idFoodRequest,
                                           @RequestParam int idResByUser,
                                           @RequestParam String token) {
        idFood = idFoodRequest;
        idRes = idResByUser;
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);

        if (idFood != 0) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.OK);
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getCart(@PathVariable String token) {
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        idFood = 0;
        return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
    }

    @GetMapping("/getFood/{token}")
    public ResponseEntity<?> getFood(@PathVariable String token) {
        System.out.println(token);
        getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
        if (token != null) {
            if (idFood != 0) {
                return new ResponseEntity<>(new ResponseData(cartService
                        .addFoodToCart(idFood, idRes, getUserNameByToken)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseData(cartService
                        .addFoodToCart(0, 0, getUserNameByToken)), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ResponseData(), HttpStatus.BAD_REQUEST);
        }
    }

    // Create order item in cart
    @PostMapping("/orderItem")
    public ResponseEntity<?> getOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        boolean isSuccess = orderService.saveOrder(getUserNameByToken,
                orderItemDTO.getIdFood(), orderItemDTO.getAmount(), orderItemDTO.getPrice());

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete order item in cart
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("id") int id) {
        boolean isSuccess = cartService.deleteItemOder(id, getUserNameByToken);

        if (id == idFood) {
            idFood = 0;
        }

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }
}
