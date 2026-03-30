package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.model.Contact;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContactMapperTest {

    @Test
    void toEntity_ShouldMapCorrectly() {
        ContactCreateRequest request = new ContactCreateRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setMessage("Hello");

        Contact contact = ContactMapper.toEntity(request);

        assertThat(contact).isNotNull();
        assertThat(contact.getName()).isEqualTo("John Doe");
        assertThat(contact.getEmail()).isEqualTo("john@example.com");
        assertThat(contact.getMessage()).isEqualTo("Hello");
        assertThat(contact.getStatus()).isEqualTo("new"); // important
    }

    @Test
    void toResponse_ShouldMapCorrectly() {
        UUID id = UUID.randomUUID();

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setMessage("Hello");

        ContactResponse response = ContactMapper.toResponse(contact);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getName()).isEqualTo("John Doe");
        assertThat(response.getEmail()).isEqualTo("john@example.com");
        assertThat(response.getMessage()).isEqualTo("Hello");
    }
}