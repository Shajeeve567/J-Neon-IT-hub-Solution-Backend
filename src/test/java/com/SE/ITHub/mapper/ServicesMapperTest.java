package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.model.Services;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServicesMapperTest {

    private final ServicesMapper mapper = new ServicesMapper();

    @Test
    void toEntity_ShouldConvertRequestDtoToEntity() {
        ServiceRequestDto dto = new ServiceRequestDto();
        dto.setTitle("Test Service");
        dto.setSlug("test-service");
        dto.setShortDescription("Description");
        dto.setIcon("icon.png");
        dto.setActive(true);

        Services entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Test Service", entity.getTitle());
        assertEquals("test-service", entity.getSlug());
        assertEquals("Description", entity.getShortDescription());
        assertEquals("icon.png", entity.getIcon());
        assertTrue(entity.isActive());
        assertEquals(0, entity.getSortOrder());
    }

    @Test
    void toResponse_ShouldConvertEntityToResponseDto() {
        UUID id = UUID.randomUUID();
        Services entity = Services.builder()
                .id(id)
                .title("Test Service")
                .slug("test-service")
                .shortDescription("Description")
                .icon("icon.png")
                .isActive(true)
                .sortOrder(5)
                .build();

        ServiceResponseDto dto = mapper.toResponse(entity);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Test Service", dto.getTitle());
        assertEquals("test-service", dto.getSlug());
        assertEquals("Description", dto.getShortDescription());
        assertEquals("icon.png", dto.getIcon());
        assertEquals(5, dto.getSortOrder());
    }

    @Test
    void updateEntity_ShouldUpdateFields() {
        Services entity = Services.builder()
                .id(UUID.randomUUID())
                .title("Original")
                .slug("original")
                .shortDescription("Original Desc")
                .icon("original.png")
                .isActive(true)
                .build();

        ServiceUpdateDto updateDto = new ServiceUpdateDto(
                "Updated",
                "updated.png",
                "updated",
                "Updated Desc",
                10,
                false
        );

        Services updated = mapper.updateEntity(entity, updateDto);

        assertEquals("Updated", updated.getTitle());
        assertEquals("updated", updated.getSlug());
        assertEquals("Updated Desc", updated.getShortDescription());
        assertEquals("updated.png", updated.getIcon());
        assertFalse(updated.isActive());
    }
}