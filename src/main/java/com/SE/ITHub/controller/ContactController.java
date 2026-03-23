package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import java.util.UUID;

>>>>>>> 793ed9b (feat(contact): getting contact by ID)
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

<<<<<<< HEAD
    @PostMapping("/post")
    public ResponseEntity<?> postContact(@RequestBody ContactCreateRequest contact){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.addContactMessage(contact));
    }
=======
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getContactByID(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.findById(id));
    }

>>>>>>> 793ed9b (feat(contact): getting contact by ID)
}
