package com.arun.eduacce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {

    @RequestMapping(value = "/contact")
    public String displayContactPage(){
        return "contact.html";
    }
}
