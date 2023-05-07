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

    SignupDTO signupDTOWaitOTP = null;
    String OTP = null;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SignupService signupService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestParam String OTPByUser) {
        ResponseData responseData = new ResponseData();
        if (OTPByUser.equals(OTP)) {
            responseData.setData(signupService.singup(signupDTOWaitOTP));
            responseData.setDesc("Đăng ký thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Đăng ký thất bại");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/postUserInfor")
    public ResponseEntity<?> postUserInfor(@RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        System.out.println(signupDTO.getUsername());
        System.out.println(signupDTO);
        if (signupService.checkUser(signupDTO.getUsername())) {
            signupDTOWaitOTP = signupDTO;
            responseData.setData(emailService.sendSimpleMail(setEmailDTO(signupDTO.getUsername())));
            responseData.setDesc("Gửi OTP thành công");
        } else {
            responseData.setDesc("Email đã tồn tại, vui lòng nhập email khác.");
            responseData.setStatusCode(400);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
