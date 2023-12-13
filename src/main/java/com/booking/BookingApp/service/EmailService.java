package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserService userService;


    public void sendEmail(
            String to, String subject, Long userId) {
        User user = userService.findOne(userId);
        String activationLink = "vanjahash";
        user.setActivationLink(activationLink);
        LocalDate activationTime = LocalDate.now();
        user.setActivationLinkDate(activationTime);

        String emailText = "http://localhost:4200/logIn";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("travelbee.team22@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(emailText);
        emailSender.send(message);
    }
}
