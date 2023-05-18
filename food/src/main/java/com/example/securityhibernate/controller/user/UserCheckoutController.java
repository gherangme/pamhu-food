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

    private String getUserNameByToken = null;
    private double totalPrice = 0;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PromotionService promotionService;

    // Post infor checkout
    @PostMapping("/postInforCheckout")
    public ResponseEntity<?> postInforCheckout(@RequestParam String token,
                                               @RequestParam double price) {
        if (token != null) {
            getUserNameByToken = jwtUtilsHelpers.getUsernameByToken(token);
            totalPrice = price;
            return new ResponseEntity<>(new ResponseData(getUserNameByToken,
                    "Post thành công thông tin checkout"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "post thất bại infor checkout",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Update voucher
    @PutMapping("/putVoucher")
    private ResponseEntity<?> putVoucher(@RequestParam String promotionCode,
                                         @RequestParam int idRes) {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(checkoutService.getUserByUsername(getUserNameByToken),
                "Lấy thành công thông tin user"));

        double promotion = promotionService.checkPromotionCode(promotionCode, idRes, getUserNameByToken);

        if (promotion != 0) {
            totalPrice -= promotion;
            list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken),
                    "Lấy thành công thông tin food checkout"));

            list.add(new ResponseData(Double.toString(totalPrice),
                    "Giảm giá thành công"));
        } else {
            list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken),
                    "Lấy thành công thông tin food checkout"));

            list.add(new ResponseData(Double.toString(totalPrice),
                    "Protion Code không hợp lệ",
                    400));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get infor checkout
    @GetMapping("/getInforCheckout")
    public ResponseEntity<?> getInforCheckout() {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(checkoutService.getUserByUsername(getUserNameByToken),
                "Lấy thành công thông tin user"));

        list.add(new ResponseData(checkoutService.getListFoodCheckout(getUserNameByToken),
                "Lấy thành công thông tin food checkout"));

        list.add(new ResponseData(Double.toString(totalPrice),
                "Lấy thành công thông tin totalPrice"));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Checkout
    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutDTO checkoutDTO) {
        int isSuccess = checkoutService.checkout(checkoutDTO);
        if (isSuccess != 0) {
            return new ResponseEntity<>(new ResponseData(sendEmailCheckout(isSuccess, checkoutDTO),
                    "Đặt hàng thành công, vui lòng kiểm tra mã đơn hàng trong email của bạn"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Đặt hàng thất bại",
                    400), HttpStatus.BAD_REQUEST);
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
