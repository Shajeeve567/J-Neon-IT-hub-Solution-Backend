package com.SE.ITHub.service;

import com.SE.ITHub.model.Contact;
import org.springframework.stereotype.Service;

@Service
public interface ContactService {
    Contact addContactMessage(Contact contact);
}
