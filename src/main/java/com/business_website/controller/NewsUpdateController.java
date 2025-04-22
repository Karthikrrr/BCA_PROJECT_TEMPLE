package com.business_website.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.business_website.dto.NewsUpdatesDto;
import com.business_website.models.NewsUpdates;
import com.business_website.service_implementation.NewsUpdatesServiceImplementation;

@Controller
public class NewsUpdateController {

    @Autowired
    private NewsUpdatesServiceImplementation newsUpdatesServiceImplementation;

    @GetMapping("/newsandupdates")
    public String getAllNewsUpdate(Model model){
        List<NewsUpdates> newsUpdates = newsUpdatesServiceImplementation.getAllNewsUpdates();
        model.addAttribute("newsUpdatesList", newsUpdates);
        return "users/newsandupdate";
    }


    //ADMIN
    @GetMapping("admin/newsandupdates")
    public String getAllNewsUpdatesForAdmin(Model model){
        List<NewsUpdates> newsUpdates = newsUpdatesServiceImplementation.getAllNewsUpdates();
        model.addAttribute("newsupdate" , newsUpdates);
        return "admin/admin-newsupdates-view";
    }

    @GetMapping("admin/newsandupdates/new")
    public String createNewsUpdatesForm(Model model){
        return "admin/create-newsupdates";
        
    }

    @PostMapping("admin/newsandupdates/new")
    public String saveNewsAndUpdates(
            @ModelAttribute("newsandupdate") NewsUpdatesDto newsUpdatesDto,
            @RequestParam("image") MultipartFile file, 
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
            newsUpdatesServiceImplementation.save(newsUpdatesDto, file);
            model.addAttribute("sucess", "Projects Added Sucessfull");
            return "redirect:/admin";
        }
        
    }

    @GetMapping("admin/newsandupdates/update/{id}")
    public String showUpdateProjectForm(@PathVariable("id") Long id, Model model){
        NewsUpdates newsUpdates = newsUpdatesServiceImplementation.getNewsUpdatesById(id);
        model.addAttribute("newsupdate", newsUpdates);
        return "admin/admin-newsupdates-update";
    }

    @PostMapping("admin/newsandupdates/update/{id}")
    public String updateProject(
            @PathVariable("id") Long id,
            @ModelAttribute("newsupdate") NewsUpdatesDto newsUpdatesDto,
            @RequestParam("image") MultipartFile file)
    {
        newsUpdatesServiceImplementation.updateNewsUpdates(newsUpdatesDto, id, file);
        return "redirect:/admin/newsandupdates";
    }

    @GetMapping("admin/newsandupdates/confirmDelete/{id}")   
    public String confirmDelete(@PathVariable("id") Long id, Model model){
        model.addAttribute("newsId" , id);
        return "admin/confirm-newsupdate-delete";
    }

    @PostMapping("admin/newsandupdates/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        newsUpdatesServiceImplementation.deleteNewsUpdate(id);
        return "redirect:/admin/newsandupdates";
    }
}
