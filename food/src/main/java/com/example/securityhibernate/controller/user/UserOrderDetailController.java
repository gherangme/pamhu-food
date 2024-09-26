package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/orderDetails")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOrderDetailController {

    int idOrder = 0;

    @Autowired
    OrderService orderService;

    // Post Id order
    @PostMapping("/{idOder}")
    public ResponseEntity<?> postIdOrder(@RequestParam int idOder) {
        System.out.println(idOder);
        if (idOder != 0) {
            idOrder = idOder;
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getInvolve() {
        return new ResponseEntity<>(new ResponseData(orderService.getInvoiceById(idOrder)), HttpStatus.OK);
    }

}
