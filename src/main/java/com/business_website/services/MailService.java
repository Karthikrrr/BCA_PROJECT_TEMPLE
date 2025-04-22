package com.business_website.services;

import org.springframework.web.multipart.MultipartFile;

public interface MailService {
    void sendMail(String messageTo, String messageSubject, String messageByUser, MultipartFile file);
}
