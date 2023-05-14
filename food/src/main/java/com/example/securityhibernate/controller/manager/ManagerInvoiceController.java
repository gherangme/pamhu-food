package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager/invoice")
public class ManagerInvoiceController {

    int idOrder = 0;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/postIdOrdder")
    public ResponseEntity<?> postIdOrdder(@RequestParam int idOrderByUser) {
        ResponseData responseData = new ResponseData();
        if (idOrderByUser != 0) {
            idOrder = idOrderByUser;
            responseData.setData(true);
            responseData.setDesc("Lấy thành công id order");
        } else {
            responseData.setDesc("Lấy thất bại id order");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getInforInvoiceById")
    public ResponseEntity<?> getInforInvoiceById() {
        ResponseData responseData = new ResponseData();
        responseData.setData(invoiceService.getInforInvoiceById(idOrder));
        responseData.setDesc("Lấy thành công thông tin invoice");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
