package com.example.securityhibernate.controller.user;

import com.example.securityhibernate.dto.CheckoutDTO;
import com.example.securityhibernate.dto.EmailDTO;
import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.CartService;
import com.example.securityhibernate.service.CheckoutService;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.PromotionService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/checkout")
public class UserCheckoutController {

    String getUserNameByToken = null;
    double totalPrice = 0;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private CartService cartService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/postInforCheckout")
    public ResponseEntity<?> postInforCheckout(@RequestParam String token, @RequestParam double price) {
        ResponseData responseData = new ResponseData();
        System.out.println(token);
        if (token != null) {
            getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
            totalPrice = price;
            responseData.setData(getUserNameByToken);
        } else {
            responseData.setDesc("post thất bại infor checkout");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/putVoucher")
    private ResponseEntity<?> putVoucher(@RequestParam String promotionCode, @RequestParam int idRes) {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        responseData.setData(checkoutService.getUserByUsername(getUserNameByToken));
        responseData.setDesc("Lấy thành công thông tin user");
        list.add(responseData);

        ResponseData responseData1 = new ResponseData();
        ResponseData responseData2 = new ResponseData();
        double promotion = promotionService.checkPromotionCode(promotionCode, idRes, getUserNameByToken);
        System.out.println(promotion);
        if (promotion != 0) {
            totalPrice -= promotion;
            responseData1.setData(cartService.getListFoods(0, 0, getUserNameByToken));
            list.add(responseData1);

            responseData2.setData(Double.toString(totalPrice));
            responseData2.setDesc("Giảm giá thành công");
            list.add(responseData2);
        } else {
            responseData1.setData(cartService.getListFoods(0, 0, getUserNameByToken));
            list.add(responseData1);

            responseData2.setData(Double.toString(totalPrice));
            responseData2.setDesc("Protion Code không hợp lệ");
            responseData2.setStatusCode(400);
            list.add(responseData2);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getInforCheckout")
    public ResponseEntity<?> getInforCheckout() {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        responseData.setData(checkoutService.getUserByUsername(getUserNameByToken));
        responseData.setDesc("Lấy thành công thông tin user");
        list.add(responseData);

        ResponseData responseData1 = new ResponseData();
        responseData1.setData(cartService.getListFoods(0, 0, getUserNameByToken));
        list.add(responseData1);

        ResponseData responseData2 = new ResponseData();
        responseData2.setData(Double.toString(totalPrice));
        list.add(responseData2);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutDTO checkoutDTO) {
        ResponseData responseData = new ResponseData();
        int isSuccess = checkoutService.checkout(checkoutDTO);
        if (isSuccess != 0) {
            responseData.setDesc("Đặt hàng thành công, vui lòng kiểm tra mã đơn hàng trong email của bạn");
            responseData.setData(sendEmailCheckout(isSuccess, checkoutDTO));
        } else {
            responseData.setData(false);
            responseData.setDesc("Đặt hàng thất bại");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
