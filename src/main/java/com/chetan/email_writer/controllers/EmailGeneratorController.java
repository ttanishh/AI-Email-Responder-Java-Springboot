package com.chetan.email_writer.controllers;

import com.chetan.email_writer.entity.EmailRequest;
import com.chetan.email_writer.service.EmailGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/email")
public class EmailGeneratorController {

    private static final Logger logger = LoggerFactory.getLogger(EmailGeneratorController.class);

    private final EmailGeneratorService emailGeneratorService;

    @Autowired
    public EmailGeneratorController(EmailGeneratorService emailGeneratorService) {
        this.emailGeneratorService = emailGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> getEmail(@RequestBody EmailRequest emailRequest) {
        logger.info("Received request: {}", emailRequest);

        // If subject is present, no need to check emailContent
        if ((emailRequest.getSubject() == null || emailRequest.getSubject().trim().isEmpty())
            && (emailRequest.getEmailContent() == null || emailRequest.getEmailContent().trim().isEmpty())) {
            logger.warn("Invalid request: Both subject and email content are empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email content: Both subject and email content are empty");
        }

        String response = emailGeneratorService.getEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }
}
