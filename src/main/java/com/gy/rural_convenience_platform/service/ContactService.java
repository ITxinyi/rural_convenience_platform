package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.Contact;
import com.gy.rural_convenience_platform.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ContactService {

    @Autowired
    private ContactMapper contactMapper;

    public boolean addContact(Contact contact) {
        contact.setContactDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println(contact);
        int result = contactMapper.insert(contact);
        if(result > 0 ) return true;
        return false;
    }
}
