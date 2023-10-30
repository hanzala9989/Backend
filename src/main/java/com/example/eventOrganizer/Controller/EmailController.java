package com.example.eventOrganizer.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventOrganizer.Entity.ContactEntity;
import com.example.eventOrganizer.ServiceImpl.EmailServiceImpl;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@RequestMapping("/email")
public class EmailController {
    private static Logger logger = LogManager.getLogger(EmailController.class);

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/send")
    public ResponseEntity<ResponseHandler> sendEmail(@RequestBody ContactEntity emailRequest) {
        logger.info("EmailController :: START :: sendEmail() ::");        
        
        String to = emailRequest.getEmail();
        String subject = emailRequest.getSubject();
        String body = emailRequest.getMessage();
        
        logger.info("EmailController :: PROCESS :: sendEmail() ::");        
        emailService.sendEmail(to, subject, body);
        logger.info("EmailController :: END :: sendEmail() ::");        

        return ResponseEntity.ok(new ResponseHandler("200", "Email Sent successfully!"));
    }
}
