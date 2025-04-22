package com.business_website.Exception;

import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "File is too large. Maximum upload size is 1MB.");
        return "redirect:/admin/projects";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String UsernameNotFoundException(){
        return "redirect:/login";
    }

    @ExceptionHandler(MailConnectException.class)
    public String UnknownHostException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Check Your Internet Connection Or SomeThing Went Wrong!!!");
        return "redirect:/placements";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String ArgNotValidException(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Password Must Be 8 Character Long!!!");
        return "redirect:/register";
    }
}
