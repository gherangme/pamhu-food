package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.EmailDTO;
import com.example.securityhibernate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam String email,
                                       @RequestParam String body,
                                       @RequestParam String subject) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setMsgBody(body);
        emailDTO.setRecipient(email);
        emailDTO.setSubject(subject);

        return new ResponseEntity<>(emailService.sendSimpleMail(emailDTO), HttpStatus.OK);
    }

}
