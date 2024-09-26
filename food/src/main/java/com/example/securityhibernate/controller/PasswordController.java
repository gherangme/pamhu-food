package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.EmailDTO;
import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.ForgotService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/password")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordController {

    String OTP = null;
    String email = null;

    @Autowired
    ForgotService forgotService;

    @Autowired
    EmailService emailService;

    // Change Password
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam String password, @RequestParam String OTPByUser) {

        if (OTP.equals(OTPByUser)) {
            return new ResponseEntity<>(new ResponseData(forgotService.changePassword(email, password),
                    "Thay đổi mật khẩu thành công, vui lòng đăng nhập lại"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "OTP không hợp lệ",
                    400), HttpStatus.BAD_REQUEST);
        }

    }

    // Check Exist User
    @PostMapping("/postUsername")
    public ResponseEntity<?> postUsername(@RequestParam String username) {
        boolean isExist = forgotService.checkUsername(username);
        if (isExist) {
            email = username;
            return new ResponseEntity<>(new ResponseData(emailService.sendSimpleMail(setEmailDTO(username)),
                    "Gửi OTP thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Email không tồn tại, vui lòng kiểm tra lại",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Set Infor send Email
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
