package com.SE.ITHub.service;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.model.Contact;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 793ed9b (feat(contact): getting contact by ID)
import java.util.UUID;

@Service
public interface ContactService {
    Contact findById(UUID id);
<<<<<<< HEAD
<<<<<<< HEAD
    ContactResponse addContactMessage(ContactCreateRequest contact);
=======
>>>>>>> 793ed9b (feat(contact): getting contact by ID)
=======
    List<Contact> findAll();
>>>>>>> e9bc41f (feat(contact): getting all contacts)
}
