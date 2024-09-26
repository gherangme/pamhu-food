package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.response.CheckoutDTO;
import com.example.securityhibernate.dto.response.EmailDTO;
import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.CheckoutService;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.PromotionService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/checkouts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCheckoutController {

    String getUserNameByToken = null;
    double totalPrice = 0;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/postInforCheckout")
    public ResponseEntity<?> getCheckoutRequest(@RequestParam String token,
                                               @RequestParam double price) {
        if (token != null) {
            getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
            totalPrice = price;
            return new ResponseEntity<>(new ResponseData(getUserNameByToken), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{promotionCode}")
    private ResponseEntity<?> updatePromotion(@RequestParam String promotionCode,
                                         @RequestParam int idRes) {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(checkoutService.getUserByUsername(getUserNameByToken)));

        double promotion = promotionService.checkPromotionCode(promotionCode, idRes, getUserNameByToken);

        if (promotion != 0) {
            totalPrice -= promotion;
            list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken)));
            list.add(new ResponseData(Double.toString(totalPrice)));
        } else {
            list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken)));
            list.add(new ResponseData(Double.toString(totalPrice)));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCheckout() {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(checkoutService.getUserByUsername(getUserNameByToken)));

        list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken)));

        list.add(new ResponseData(Double.toString(totalPrice)));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Checkout
    @PutMapping
    public ResponseEntity<?> updateCheckout(@RequestBody CheckoutDTO checkoutDTO) {
        int isSuccess = checkoutService.checkout(checkoutDTO);
        if (isSuccess != 0) {
            return new ResponseEntity<>(new ResponseData(sendEmailCheckout(isSuccess, checkoutDTO)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    // Send mail checkout
    private String sendEmailCheckout(int isSucces, CheckoutDTO checkoutDTO) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRecipient(checkoutDTO.getUsername());
        emailDTO.setSubject("[Pamhu Food]");
        emailDTO.setMsgBody("Mã đơn hàng " +
                "của bạn là #" + isSucces + ", tổng số tiền là " + totalPrice + " VND. Cảm ơn khách hàng " +
                checkoutDTO.getFullName() + " đã sử dụng Pamhu Food.");
        return emailService.sendSimpleMail(emailDTO);
    }

}
