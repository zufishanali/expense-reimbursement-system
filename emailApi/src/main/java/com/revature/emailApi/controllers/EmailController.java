package com.revature.emailApi.controllers;

import com.revature.emailApi.dtos.EmailDTO;
import com.revature.emailApi.services.EmailService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendEmail")
public class EmailController {

    @Setter(onMethod = @__({@Autowired}) )
    private EmailService emailService;

    @PostMapping
    public ResponseEntity handleEmailRequests(@RequestBody EmailDTO emailRequest){
        System.out.println("Request received .. processing: " + emailRequest);
        try {
            String response = emailService.sendEmail(emailRequest);
            return ResponseEntity.ok(response);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error sending email " + e.getMessage());
        }

    }
}
