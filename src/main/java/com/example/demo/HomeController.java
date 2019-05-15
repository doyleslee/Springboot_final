package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

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
    public String processMessage(@Valid Message message, BindingResult result) {
        if(result.hasErrors())
            return "newmessage";
        messageRepository.save(message);
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
