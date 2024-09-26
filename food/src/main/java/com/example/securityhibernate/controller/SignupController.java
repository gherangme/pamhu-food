package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.EmailDTO;
import com.example.securityhibernate.dto.request.SignupDTO;
import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.SignupService;
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
@RequestMapping("/sign-up")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupController {

    SignupDTO waitingOTP = null;
    String OTP = null;

    @Autowired
    EmailService emailService;

    @Autowired
    SignupService signupService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestParam String OTPByUser) {
        if (OTPByUser.equals(OTP)) {
            return new ResponseEntity<>(new ResponseData(signupService.singup(waitingOTP),
                    "Đăng ký thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Đăng ký thất bại",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/otp")
    public ResponseEntity<?> createOTP(@Valid @RequestBody SignupDTO signupDTO) {
        if (signupService.checkUser(signupDTO.getUsername())) {
            waitingOTP = signupDTO;
            return new ResponseEntity<>(new ResponseData(emailService.sendSimpleMail(sendEmail(signupDTO.getUsername())),
                    "Gửi OTP thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Email đã tồn tại, vui lòng nhập email khác.",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    private EmailDTO sendEmail(String email) {

        Random random = new Random();
        OTP = Integer.toString(
                random.ints(1000, 9999)
                        .findFirst()
                        .getAsInt());

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(5));

        if (new Date().after(expireTime)) {
            OTP = null;
        }

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setMsgBody("Your OTP is " + OTP + ". Please enter in 5 minutes.");
        emailDTO.setRecipient(email);
        emailDTO.setSubject("[Pamhu Food]");

        return emailDTO;
    }

}
