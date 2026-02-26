package com.SE.ITHub.controller;

import com.SE.ITHub.model.Contact;
import com.SE.ITHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/post")
    public ResponseEntity<?> postContact(@RequestBody Contact contact){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.addContactMessage(contact));
    }
}
