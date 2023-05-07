package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.EmailDTO;
import com.example.securityhibernate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDTO.getRecipient());
            mailMessage.setText(emailDTO.getMsgBody());
            mailMessage.setSubject(emailDTO.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Gửi mail thành công !";
        }catch (Exception e) {
            return "Gửi mail thất bại !";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDTO emailDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Setting multipart as true for attachments to be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDTO.getRecipient());
            mimeMessageHelper.setText(emailDTO.getMsgBody());
            mimeMessageHelper.setSubject(emailDTO.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(emailDTO.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Gứi mail thành công";
        }catch (MessagingException e) {
            return "Gửi mail thất bại !";
        }
    }
}
