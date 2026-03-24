package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactTagRequest;
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

    @PutMapping("/tags")
    public ResponseEntity<?> editContact(@RequestBody ContactTagRequest contact){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.updateContactMessage(contact));
    }
}
