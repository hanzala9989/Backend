package com.example.eventOrganizer.ServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl {
    private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String body) {
        logger.info("EmailServiceImpl :: START :: sendEmail() ::");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        logger.info("EmailServiceImpl :: END :: sendEmail() ::");
    }

    public String generateRegistrationEmailContent(String username, String RegisterDate, String organizerName) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("RegisterDate", RegisterDate);
        context.setVariable("organizerName", organizerName);

        return templateEngine.process("registration-email-template", context);
    }

     public void sendRegistrationEmail(String to, String subject,String username, String RegisterDate, String organizerName) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Registration Confirmation for XYZ Event");

        String htmlContent = generateRegistrationEmailContent(username, RegisterDate, organizerName);
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
