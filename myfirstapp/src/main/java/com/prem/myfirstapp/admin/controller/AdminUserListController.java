package com.prem.myfirstapp.admin.controller;

import com.prem.myfirstapp.admin.dto.AdminUserListResponse;
import com.prem.myfirstapp.admin.dto.UpdateUserListStatusRequest;
import com.prem.myfirstapp.admin.service.AdminUserListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserListController {

    private final AdminUserListService adminUserListService;

    @GetMapping
    public List<AdminUserListResponse> getAllUsers() {
        return adminUserListService.getAllUsers();
    }

    @PutMapping("/{userId}/status")
    public AdminUserListResponse updateStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserListStatusRequest request,
            Authentication authentication) {
        return adminUserListService.updateStatus(userId, request.status(), authentication.getName());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            Authentication authentication) {
        adminUserListService.deleteUser(userId, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}