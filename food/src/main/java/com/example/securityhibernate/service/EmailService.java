package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.EmailDTO;

public interface EmailService {

    //Send mail text
    String sendSimpleMail(EmailDTO emailDTO);

    //Send mail attach file
    String sendMailWithAttachment(EmailDTO emailDTO);

}
