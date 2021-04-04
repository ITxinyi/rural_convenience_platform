package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.Contact;
import com.gy.rural_convenience_platform.service.ContactService;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contact")
    public Map<String,Object> addContact(String name,String email,String subject,String content){
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setSubject(subject);
        contact.setContent(content);
        boolean result = contactService.addContact(contact);
        return ResponseCode.ok(result);
    }

}
