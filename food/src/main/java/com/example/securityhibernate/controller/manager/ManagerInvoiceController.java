package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.ManagerInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager/invoice")
public class ManagerInvoiceController {

    private int idOrder = 0;

    @Autowired
    private ManagerInvoiceService managerInvoiceService;

    // Post Id order
    @PostMapping("/postIdOrder")
    public ResponseEntity<?> postIdOrder(@RequestParam int idOrderByUser) {
        if (idOrderByUser != 0) {
            idOrder = idOrderByUser;
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thành công id order"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Lấy thất bại id order",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getInforInvoiceById")
    public ResponseEntity<?> getInforInvoiceById() {
        return new ResponseEntity<>(new ResponseData(managerInvoiceService.getInforInvoiceById(idOrder),
                "Lấy thành công thông tin invoice"), HttpStatus.OK);
    }

}
