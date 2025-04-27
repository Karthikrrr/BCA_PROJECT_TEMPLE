package com.business_website.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.ArthiDto;
import com.business_website.dto.ProjectsDto;
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
public class TempleController {

    
    @Autowired
    private  TempleServiceImplementation projectsServiceImplementation;


    @Autowired
    private  JavaMailSender mailSender;

    @GetMapping("/projects")
    public String getAllProjects(Model model){
        List<Temple> projects = projectsServiceImplementation.getAllProjects();
        model.addAttribute("projects" , projects);
        return "users/projects";
    }

    @GetMapping("/projects/{id}")
    public String getOneProjectById(@PathVariable("id") Long id, Model model){
        Temple temple = projectsServiceImplementation.getProjectById(id);
        model.addAttribute("temple", temple);
        return "users/TempleView";
    }
    
@GetMapping("/temple/{id}/arthi/book")
public String showArthiForm(@PathVariable Long id, Model model) {
    Temple temple = projectsServiceImplementation.getProjectById(id);
    ArthiDto arthiDto = new ArthiDto();
    arthiDto.setTempleName(temple.getTitle()); 
    model.addAttribute("arthiDto", arthiDto);
    model.addAttribute("temple", temple);
    return "users/arthi-booking-view"; 
}

@GetMapping("/temple/{id}/donate")
public String getQrCode(@PathVariable Long id, Model model){
    Temple temple = projectsServiceImplementation.getProjectById(id);
    model.addAttribute("temple", temple);
    return "users/qrcode";
}

@PostMapping("/temple/{id}/arthi/generate-pdf")
public void generatePdf(
        @PathVariable("id") Long id, 
        @ModelAttribute ArthiDto dto, 
        HttpServletResponse response) throws IOException {

    try {
        if (dto.getNumberOfPpl() <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Number of people must be positive");
            return;
        }

        Temple temple = projectsServiceImplementation.getProjectById(id);
        int total = dto.getNumberOfPpl() * temple.getArthiAmount();
        dto.setTotalAmount("₹" + total);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Temple Arthi Booking Details", titleFont));
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
        response.setHeader("Content-Disposition", "attachment; filename=arthi-booking-details.pdf");
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
    helper.setSubject("Your Arthi Booking Confirmation");
    helper.setText("Dear " + userName + ",\n\n"
            + "Thank you for booking an arthi at our temple. "
            + "Your booking details are attached.\n\n"
            + "NOTE: Please bring an ID proof when you visit.\n\n"
            + "Regards,\nTemple Management");

    DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
    helper.addAttachment("Arthi_Booking_Details.pdf", dataSource);

    mailSender.send(message);
}



    //ADMIN Controller 
    @GetMapping("admin/projects")
    public String getAllProjectsForAdmin(Model model){
        List<Temple> projects = projectsServiceImplementation.getAllProjects();
        model.addAttribute("projects" , projects);
        return "admin/admin-project-view";
    }
    

    @GetMapping("admin/projects/new")
    public String createProjectsForm(Model model){
        return "admin/create-project";
    }

    @PostMapping("admin/projects/new")
    public String saveProject(
            @ModelAttribute("project") ProjectsDto projectDto,
            @RequestParam("image") MultipartFile file,
            @RequestParam("qrcode") MultipartFile file2, 
            Model model){
        if(file.isEmpty()){
            model.addAttribute("message", "File is Empty!!!");
            return "admin/create-project";
        }
        else if(file.getSize() >  1048576){
            model.addAttribute("message", "FileSize is Greater Than 1MB");
            return "admin/create-project";
        }
        else{
            projectsServiceImplementation.save(projectDto, file, file2);
            model.addAttribute("sucess", "Projects Added Sucessfull");
            return "redirect:/admin";
        }
        
    }

    @GetMapping("admin/projects/update/{id}")
    public String showUpdateProjectForm(@PathVariable("id") Long id, Model model){
        System.out.println("id = " + id);
        Temple project = projectsServiceImplementation.getProjectById(id);
        model.addAttribute("project", project);
        return "admin/admin-project-update";
    }

    @PostMapping("admin/projects/update/{id}")
    public String updateProject(
            @PathVariable("id") Long id,
            @ModelAttribute("project") ProjectsDto projectDto,
            @RequestParam("image") MultipartFile file, Model model)
    {
        System.out.println("id = " + id);
        projectsServiceImplementation.updateProject(projectDto, id, file);
        model.addAttribute("sucess", "Projects Updated Sucessfull");
        return "redirect:/admin/projects";
    }

    @GetMapping("admin/projects/confirmDelete/{id}")   
    public String confirmDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("projectId" , id);
        return "admin/confirm-project-delete";
    }

    @PostMapping("admin/projects/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        projectsServiceImplementation.deleteProject(id);
        return "redirect:/admin/projects";
    }

}
