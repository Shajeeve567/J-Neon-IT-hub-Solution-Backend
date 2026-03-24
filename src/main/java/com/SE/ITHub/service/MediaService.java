package com.SE.ITHub.service;

import com.SE.ITHub.dto.MediaResponseDto;
import com.SE.ITHub.model.Media;
import com.SE.ITHub.repository.MediaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaService {
    public Media upload(MultipartFile file, String altText,  String caption);
    public void delete(UUID mediaId);
}
