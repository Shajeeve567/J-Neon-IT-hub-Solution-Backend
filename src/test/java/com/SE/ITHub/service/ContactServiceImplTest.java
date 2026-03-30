package com.SE.ITHub.service;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.dto.ContactTagRequest;
import com.SE.ITHub.exception.ContactNotFoundException;
import com.SE.ITHub.model.Contact;
import com.SE.ITHub.repository.ContactRepository;
import com.SE.ITHub.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private UUID testId;
    private ContactCreateRequest createRequest;
    private Contact contact;
    private ContactResponse contactResponse;
    private ContactTagRequest tagRequest;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

        createRequest = new ContactCreateRequest();
        createRequest.setName("John Doe");
        createRequest.setEmail("john@example.com");
        createRequest.setMessage("Test message");

        contact = new Contact();
        contact.setId(testId);
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setMessage("Test message");
        contact.setStatus("new");

        contactResponse = new ContactResponse();
        contactResponse.setId(testId);
        contactResponse.setName("John Doe");
        contactResponse.setEmail("john@example.com");
        contactResponse.setMessage("Test message");

        tagRequest = new ContactTagRequest();
        tagRequest.setId(testId);
        tagRequest.setStatus("resolved");
    }

    @Test
    void addContactMessage_ShouldSaveAndReturnResponse() {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactResponse response = contactService.addContactMessage(createRequest);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(testId);
        assertThat(response.getName()).isEqualTo("John Doe");

        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void updateContactMessage_ShouldUpdateStatusAndReturnResponse() {
        when(contactRepository.findById(testId)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactResponse response = contactService.updateContactMessage(tagRequest);

        assertThat(response).isNotNull();
        assertThat(contact.getStatus()).isEqualTo("resolved");

        verify(contactRepository).findById(testId);
        verify(contactRepository).save(contact);
    }

    @Test
    void updateContactMessage_WhenContactNotFound_ShouldThrowContactNotFoundException() {
        when(contactRepository.findById(testId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.updateContactMessage(tagRequest))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining(testId.toString());

        verify(contactRepository).findById(testId);
        verify(contactRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnContact() {
        when(contactRepository.findById(testId)).thenReturn(Optional.of(contact));

        Contact result = contactService.findById(testId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getName()).isEqualTo("John Doe");

        verify(contactRepository).findById(testId);
    }

    @Test
    void findById_WhenContactNotFound_ShouldThrowContactNotFoundException() {
        when(contactRepository.findById(testId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.findById(testId))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining(testId.toString());

        verify(contactRepository).findById(testId);
    }

    @Test
    void findAll_ShouldReturnAllContacts() {
        List<Contact> contacts = Arrays.asList(contact);
        when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> result = contactService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(testId);

        verify(contactRepository).findAll();
    }

    @Test
    void deleteContact_WhenContactNotFound_ShouldThrowContactNotFoundException() {
        when(contactRepository.findById(testId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.deleteContact(testId))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining(testId.toString());

        verify(contactRepository, never()).deleteById(any());
    }
}