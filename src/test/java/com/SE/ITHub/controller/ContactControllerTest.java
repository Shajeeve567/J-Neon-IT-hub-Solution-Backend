package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.dto.ContactTagRequest;
import com.SE.ITHub.model.Contact;
import com.SE.ITHub.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private ObjectMapper objectMapper;
    private UUID testId;
    private ContactCreateRequest createRequest;
    private ContactResponse contactResponse;
    private Contact contact;
    private ContactTagRequest tagRequest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testId = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

        createRequest = new ContactCreateRequest();
        createRequest.setName("John Doe");
        createRequest.setEmail("john@example.com");
        createRequest.setMessage("Test message");

        contactResponse = new ContactResponse();
        contactResponse.setId(testId);
        contactResponse.setName("John Doe");
        contactResponse.setEmail("john@example.com");
        contactResponse.setMessage("Test message");

        contact = new Contact();
        contact.setId(testId);
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setMessage("Test message");
        contact.setStatus("new");

        tagRequest = new ContactTagRequest();
        tagRequest.setId(testId);
        tagRequest.setStatus("resolved");
    }

    @Test
    void postContact_ShouldReturnOk() throws Exception {
        when(contactService.addContactMessage(any(ContactCreateRequest.class)))
                .thenReturn(contactResponse);

        mockMvc.perform(post("/contact/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void editContact_ShouldReturnOk() throws Exception {
        when(contactService.updateContactMessage(any(ContactTagRequest.class)))
                .thenReturn(contactResponse);

        mockMvc.perform(put("/contact/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()));
    }

    @Test
    void getContactByID_ShouldReturnContact() throws Exception {
        // Note: findById returns Contact, not ContactResponse
        when(contactService.findById(testId)).thenReturn(contact);

        mockMvc.perform(get("/contact/get/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getAllContacts_ShouldReturnList() throws Exception {
        // Note: findAll returns List<Contact>, not List<ContactResponse>
        List<Contact> contacts = Arrays.asList(contact);
        when(contactService.findAll()).thenReturn(contacts);

        mockMvc.perform(get("/contact/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void deleteAllContact_ShouldReturnOk() throws Exception {
        String successMessage = "Contact deleted successfully";
        when(contactService.deleteContact(testId)).thenReturn(successMessage);

        mockMvc.perform(delete("/contact/delete/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }
}