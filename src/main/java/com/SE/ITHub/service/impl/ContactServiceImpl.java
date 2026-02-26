package com.SE.ITHub.service.impl;

import com.SE.ITHub.model.Contact;
import com.SE.ITHub.repository.ContactRepository;
import com.SE.ITHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact addContactMessage(Contact contact){
        return  contactRepository.save(contact);
    }
}
