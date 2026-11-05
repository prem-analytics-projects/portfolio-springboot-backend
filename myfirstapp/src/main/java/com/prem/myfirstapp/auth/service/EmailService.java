package com.prem.myfirstapp.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendVerification(String to, String link) {
        SimpleMailMessage m = new SimpleMailMessage();
        m.setTo(to);
        m.setSubject("Verify your email");
        m.setText("Click the link to verify your email (valid for 30 minutes):\n" + link);
        mailSender.send(m);
    }
}
