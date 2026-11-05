package com.prem.myfirstapp.auth.service;

import com.prem.myfirstapp.auth.dto.LoginRequest;
import com.prem.myfirstapp.auth.dto.LoginResponse;
import com.prem.myfirstapp.auth.model.Role;
import com.prem.myfirstapp.auth.model.User;
import com.prem.myfirstapp.auth.repository.UserRepository;
import com.prem.myfirstapp.common.exception.AccountNotActiveException;
import com.prem.myfirstapp.common.exception.InvalidCredentialsException;
import com.prem.myfirstapp.common.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public LoginResponse login(LoginRequest req) {
        User u = userRepo.findByEmailIgnoreCase(req.email().trim())
                .orElseThrow(InvalidCredentialsException::new);

        if (!encoder.matches(req.password(), u.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // status checks only after the password is confirmed correct
        if (u.getEmailVerifiedAt() == null) throw new AccountNotActiveException("EMAIL_NOT_VERIFIED");
        if ("PENDING".equals(u.getApprovalStatus())) throw new AccountNotActiveException("PENDING_APPROVAL");
        if ("REJECTED".equals(u.getApprovalStatus())) throw new AccountNotActiveException("ACCOUNT_REJECTED");

        List<String> roles = u.getRoles().stream().map(Role::getRoleName).toList();
        return new LoginResponse(jwt.generate(u.getId(), roles), u.getFirstName(), roles);
    }
}
