package com.booking.BookingApp.controller;


import com.booking.BookingApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail(@RequestParam("userId") Long id) {
        emailService.sendEmail("jevtic.valentina02@gmail.com", "Account activation", id);
        return  "Email sent successfully!";
    }
}
