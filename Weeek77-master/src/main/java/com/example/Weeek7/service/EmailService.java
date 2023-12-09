package com.example.Weeek7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${gmail}")
    private String mail;

    public void sendEmail(String toEmail, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(toEmail);
        message.setSubject("Активируйте свой аккаунт");
        message.setText("Для активации Вашего аккаунта, пройдите по ссылке: \n\n" + activationLink);
        javaMailSender.send(message);
    }
}
