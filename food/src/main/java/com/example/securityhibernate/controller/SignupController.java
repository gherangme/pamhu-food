package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.EmailDTO;
import com.example.securityhibernate.dto.SignupDTO;
import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.EmailService;
import com.example.securityhibernate.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {

    private SignupDTO signupDTOWaitOTP = null;
    private String OTP = null;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SignupService signupService;

    // Add User
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestParam String OTPByUser) {
        if (OTPByUser.equals(OTP)) {
            return new ResponseEntity<>(new ResponseData(signupService.singup(signupDTOWaitOTP),
                    "Đăng ký thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Đăng ký thất bại",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Post User Infor
    @PostMapping("/postUserInfor")
    public ResponseEntity<?> postUserInfor(@RequestBody SignupDTO signupDTO) {
        if (signupService.checkUser(signupDTO.getUsername())) {
            signupDTOWaitOTP = signupDTO;
            return new ResponseEntity<>(new ResponseData(emailService.sendSimpleMail(setEmailDTO(signupDTO.getUsername())),
                    "Gửi OTP thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Email đã tồn tại, vui lòng nhập email khác.",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    //  Send OTP By Mail
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
