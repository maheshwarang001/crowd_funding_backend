package com.example.crowd_funding_backend.service.email;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String link){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Verify your email address");
        mailMessage.setText("Please click the following link to verify your email address:\n" + link);
        mailMessage.setSentDate(new Date());

        try {
            mailSender.send(mailMessage);
            log.info("Email sent successfully.");
        } catch (Exception e) {
            log.error("Error sending email: " + e.getMessage(), e);
        }
    }


}
