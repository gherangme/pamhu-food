package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manager/promotion")
public class ManagerPromotionController {

    private int idPromotion = 0;

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/postIdPromotion")
    public ResponseEntity<?> postIdPromotion(@RequestParam int id) {
        if (id != 0) {
            idPromotion = id;
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thành công id promotion"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Lấy thất bại id promotion",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatePromotion")
    public ResponseEntity<?> updatePromotion(@RequestParam int id, @RequestParam String token) {
        boolean isSuccess = promotionService.updatePromotion(id, token);

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Cập nhật thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Cập nhật thất bại",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getInforPromotion")
    public ResponseEntity<?> getInforPromotion() {
        List<ResponseData> list = new ArrayList<>();

        // Get Infor Promotion
        list.add(new ResponseData(promotionService.getPromotionById(idPromotion),
                "Lấy thành công thông tin promotion"));

        // Get All Promotion
        list.add(new ResponseData(promotionService.getAllPromotion(),
                "Lấy thành công danh sách promotion"));

        return new ResponseEntity(list, HttpStatus.OK);
    }

}
