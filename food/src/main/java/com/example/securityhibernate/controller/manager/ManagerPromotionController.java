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

    int idPromotion = 0;

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/postIdPromotion")
    public ResponseEntity<?> postIdPromotion(@RequestParam int id) {
        ResponseData responseData = new ResponseData();
        if (id != 0) {
            idPromotion = id;
            responseData.setData(true);
            responseData.setDesc("Lấy thành công id promotion");
        } else {
            responseData.setData(false);
            responseData.setDesc("Lấy thất bại id promotion");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/updatePromotion")
    public ResponseEntity<?> updatePromotion(@RequestParam int id, @RequestParam String token) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = promotionService.updatePromotion(id, token);

        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Cập nhật thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Cập nhật thất bại");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getInforPromotion")
    public ResponseEntity<?> getInforPromotion() {
        List<ResponseData> list = new ArrayList<>();

        // Get Infor Promotion
        ResponseData responseData = new ResponseData();
        responseData.setData(promotionService.getPromotionById(idPromotion));
        responseData.setDesc("Lấy thành công thông tin promotion");
        list.add(responseData);

        // Get All Promotion
        ResponseData responseData1 = new ResponseData();
        responseData1.setData(promotionService.getAllPromotion());
        responseData1.setDesc("Lấy thành công danh sách promotion");
        list.add(responseData1);

        return new ResponseEntity(list, HttpStatus.OK);
    }

}
