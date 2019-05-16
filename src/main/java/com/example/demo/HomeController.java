package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "messageboard";
    }

    @GetMapping("/add")
    public String addMessage(Model model) {
        model.addAttribute("message", new Message());
        return "newmessage";
    }

    @PostMapping("/process")
    public String processMessage(@Valid @ModelAttribute Message message, BindingResult result,
                                 @RequestParam("file") MultipartFile file) {
        if(result.hasErrors())
            return "newmessage";
        if(file.isEmpty()) {
            message.setPicture("...");
            messageRepository.save(message);
            //return "redirect:/add";
        } else {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                message.setPicture(uploadResult.get("url").toString());
                messageRepository.save(message);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add";
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "newmessage";
    }

    @RequestMapping("/detail/{id}")
    public String detailTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String delTask(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }


}
