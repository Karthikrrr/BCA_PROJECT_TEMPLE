package com.business_website.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.business_website.dto.ArthiDto;
import com.business_website.dto.SpecialPoojaAmount;
import com.business_website.models.Temple;
import com.business_website.service_implementation.TempleServiceImplementation;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class specialPooja {

    @Autowired
    private  TempleServiceImplementation projectsServiceImplementation;


    @Autowired
    private  JavaMailSender mailSender;

    @GetMapping("/temple/{id}/specialPooja/book")
public String showArthiForm(@PathVariable Long id, Model model) {
    Temple temple = projectsServiceImplementation.getProjectById(id);
    ArthiDto arthiDto = new ArthiDto();
    arthiDto.setTempleName(temple.getTitle()); 
    model.addAttribute("arthiDto", arthiDto);
    model.addAttribute("temple", temple);
    return "users/specialpooja-booking-view"; 
}

@PostMapping("/temple/{id}/specialPooja/generate-pdf")
public void generatePdf(
        @PathVariable("id") Long id, 
        @ModelAttribute SpecialPoojaAmount dto, 
        HttpServletResponse response) throws IOException {

    try {
        if (dto.getNumberOfPpl() <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Number of people must be positive");
            return;
        }

        Temple temple = projectsServiceImplementation.getProjectById(id);
        int total = dto.getNumberOfPpl() * temple.getSpecialPoojaAmount();
        dto.setTotalAmount("₹" + total);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Temple Special POOJA Booking Details", titleFont));
        document.add(Chunk.NEWLINE);
        
        addFormattedLine(document, "Temple Name: ", dto.getTempleName(), boldFont, normalFont);
        addFormattedLine(document, "User Name: ", dto.getUserName(), boldFont, normalFont);
        addFormattedLine(document, "Email: ", dto.getEmail(), boldFont, normalFont);
        addFormattedLine(document, "Phone Number: ", dto.getPhoneNumber(), boldFont, normalFont);
        addFormattedLine(document, "Date: ", dto.getDate(), boldFont, normalFont);
        addFormattedLine(document, "Time: ", dto.getTime(), boldFont, normalFont);
        addFormattedLine(document, "Number of People: ", String.valueOf(dto.getNumberOfPpl()), boldFont, normalFont);
        addFormattedLine(document, "Total Amount: ", dto.getTotalAmount(), boldFont, normalFont);
        
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Thank you for your booking!", boldFont));
        document.add(new Paragraph("NOTE: Please bring an ID proof!!", boldFont));

        document.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pooja-booking-details.pdf");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();

        sendConfirmationEmail(dto.getEmail(), dto.getUserName(), baos.toByteArray());

    } catch (DocumentException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
    } catch (MessagingException e) {
    }
}


private void addFormattedLine(Document document, String label, String value, Font boldFont, Font normalFont) 
        throws DocumentException {
    Paragraph p = new Paragraph();
    p.add(new Chunk(label, boldFont));
    p.add(new Chunk(value != null ? value : "", normalFont));
    document.add(p);
}

private void sendConfirmationEmail(String toEmail, String userName, byte[] pdfBytes) 
        throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(toEmail);
    helper.setSubject("Your Special Pooja Booking Confirmation");
    helper.setText("Dear " + userName + ",\n\n"
            + "Thank you for booking an special Pooja at our temple. "
            + "Your booking details are attached.\n\n"
            + "NOTE: Please bring an ID proof when you visit.\n\n"
            + "Regards,\nTemple Management");

    DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
    helper.addAttachment("pooja_Booking_Details.pdf", dataSource);

    mailSender.send(message);
}


}
