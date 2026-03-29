package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/post")
    public ResponseEntity<?> postContact(@RequestBody ContactCreateRequest contact){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.addContactMessage(contact));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAllContact(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.deleteContact(id));
    }
}
