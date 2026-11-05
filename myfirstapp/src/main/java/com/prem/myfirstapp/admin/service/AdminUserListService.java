package com.prem.myfirstapp.admin.service;

import com.prem.myfirstapp.admin.dto.AdminUserListResponse;
import com.prem.myfirstapp.auth.model.Role;
import com.prem.myfirstapp.auth.model.User;
import com.prem.myfirstapp.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserListService {

    private final UserRepository userRepo;

    @Transactional(readOnly = true)
    public List<AdminUserListResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AdminUserListResponse updateStatus(Long userId, String status, String adminEmail) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User admin = userRepo.findByEmailIgnoreCase(adminEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin not found"));

        user.setApprovalStatus(status);

        if ("PENDING".equals(status)) {
            user.setApprovedBy(null);
            user.setApprovedAt(null);
        } else {
            user.setApprovedBy(admin.getId());
            user.setApprovedAt(OffsetDateTime.now());
        }

        return toResponse(userRepo.save(user));
    }

    @Transactional
    public void deleteUser(Long userId, String adminEmail) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getEmail().equalsIgnoreCase(adminEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot delete your own account");
        }

        userRepo.delete(user);
    }

    private AdminUserListResponse toResponse(User u) {
        return new AdminUserListResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getMobile(),
                u.getEmailVerifiedAt(),
                u.getApprovalStatus(),
                u.getApprovedBy(),
                u.getApprovedAt(),
                u.getCreatedAt(),
                u.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet())
        );
    }
}