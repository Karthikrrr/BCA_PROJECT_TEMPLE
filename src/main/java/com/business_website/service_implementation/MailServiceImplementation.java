package com.business_website.service_implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.services.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImplementation implements  MailService{

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendMail(String messageTo, String messageSubject, String messageByUser, MultipartFile file) {

        MimeMessage messages = javaMailSender.createMimeMessage();
        MimeMessageHelper message;

        try {
            message = new MimeMessageHelper(messages, true);
            message.setTo(messageTo);
            message.setSubject(messageSubject);
            message.setText(messageByUser , true);
            message.addAttachment(file.getOriginalFilename(), file);
            javaMailSender.send(messages);
            System.out.println("Sent Sucess fully");
        } catch (MessagingException e) {

        }
    }

   
}
