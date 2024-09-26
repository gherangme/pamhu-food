package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.ManagerInvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerInvoiceController {

    @NonFinal
    int idOrder = 0;

    ManagerInvoiceService managerInvoiceService;

    @PostMapping("/{idOrder}")
    public ResponseEntity<?> getIdOrder(@PathVariable int idOrderRequest) {
        if (idOrderRequest != 0) {
            idOrder = idOrderRequest;
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDetailOrder")
    public ResponseEntity<?> getDetailOrder() {
        return new ResponseEntity<>(new ResponseData(managerInvoiceService.getInvoiceById(idOrder)), HttpStatus.OK);
    }

}
