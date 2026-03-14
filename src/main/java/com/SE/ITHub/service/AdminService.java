package com.SE.ITHub.service;

import com.SE.ITHub.dto.CreateAdminRequest;
import com.SE.ITHub.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    User createAdmin(CreateAdminRequest request);
    List<User> getAllUsers();
}