package com.chetan.email_writer.controllers;

import com.chetan.email_writer.entity.EmailRequest;
import com.chetan.email_writer.service.EmailGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
// @CrossOrigin(origins = "*")  // Remove this annotation

public class EmailGeneratorController {

    private final EmailGeneratorService emailGeneratorService;

    @Autowired
    public EmailGeneratorController(EmailGeneratorService emailGeneratorService) {
        this.emailGeneratorService = emailGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> getEmail(@RequestBody EmailRequest emailRequest) {
        if (emailRequest == null || emailRequest.getEmailContent() == null || emailRequest.getEmailContent().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email content");
        }

        String response = emailGeneratorService.getEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }
}
