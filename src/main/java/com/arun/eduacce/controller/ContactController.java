package com.arun.eduacce.controller;

import com.arun.eduacce.model.Contact;
import com.arun.eduacce.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ContactController {


    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact")
    public String displayContactPage() {
        return "contact.html";
    }

//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name,
//                                    @RequestParam String mobileNum,
//                                    @RequestParam String email,
//                                    @RequestParam String subject,
//                                    @RequestParam String message) {
//        log.info("Name: "+ name);
//        log.info("Mobile number: "+mobileNum);
//        log.info("Email Address: "+email);
//        log.info("Subject: "+subject);
//        log.info("Message: "+message);
//
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping("/saveMsg")
    public ModelAndView saveMessage(Contact contact){
        contactService.saveMessageDetails(contact);
        return new ModelAndView("redirect:/contact");
    }
}
