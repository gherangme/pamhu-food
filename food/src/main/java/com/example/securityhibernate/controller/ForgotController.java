package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.EmailDTO;
import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.ForgotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/forgot")
public class ForgotController {

    String OTP = null;
    String email = null;

    @Autowired
    private ForgotService forgotService;

    @Autowired
    private EmailService emailService;

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam String password, @RequestParam String OTPByUser) {
        ResponseData responseData = new ResponseData();
        if (OTP.equals(OTPByUser)) {
            responseData.setData(forgotService.changPassword(email, password));
            responseData.setDesc("Thay đổi mật khẩu thành công, vui lòng đăng nhập lại");
        } else {
            responseData.setDesc("OTP không hợp lệ");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/postUsername")
    public ResponseEntity<?> postUsername(@RequestParam String username) {
        ResponseData responseData = new ResponseData();
        boolean isExist = forgotService.checkUsername(username);
        if (isExist) {
            email = username;
            responseData.setData(emailService.sendSimpleMail(setEmailDTO(username)));
            responseData.setDesc("Gửi OTP thành công");
        } else {
            responseData.setDesc("Email không tồn tại, vui lòng kiểm tra lại");
            responseData.setStatusCode(400);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    private EmailDTO setEmailDTO(String email) {

        // Create OTP
        Random random = new Random();
        OTP = Integer.toString(
                random.ints(1000, 9999)
                        .findFirst()
                        .getAsInt());

        // Set expire time for OTP
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(5));

        if (new Date().after(expireTime)) {
            OTP = null;
        }

        // Set emailDTO
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setMsgBody("Your OTP is " + OTP + ". Please enter in 5 minutes.");
        emailDTO.setRecipient(email);
        emailDTO.setSubject("[Pamhu Food]");

        return emailDTO;
    }

}
