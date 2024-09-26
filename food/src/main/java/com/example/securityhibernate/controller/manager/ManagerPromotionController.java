package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.PromotionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/managers/promotions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerPromotionController {

    @NonFinal
    private int idPromotion = 0;

    PromotionService promotionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getIdPromotion(@PathVariable int id) {
        if (id != 0) {
            idPromotion = id;
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable int id, @RequestParam String token) {
        boolean isSuccess = promotionService.updatePromotion(id, token);

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPromotion() {
        List<ResponseData> list = new ArrayList<>();

        list.add(new ResponseData(promotionService.getPromotionById(idPromotion)));

        list.add(new ResponseData(promotionService.getAllPromotions()));

        return new ResponseEntity(list, HttpStatus.OK);
    }

}
