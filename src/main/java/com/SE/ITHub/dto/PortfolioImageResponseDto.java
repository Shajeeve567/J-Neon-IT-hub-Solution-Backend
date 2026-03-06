package com.SE.ITHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PortfolioImageResponseDto {

    private UUID id;
    private UUID portfolioItemId;
    private String imageUrl;
    private Boolean isCover;
    private Integer sortOrder;
    private String altTextOverride;
}