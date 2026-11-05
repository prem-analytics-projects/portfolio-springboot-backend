package com.prem.myfirstapp.auth.service;

import com.prem.myfirstapp.auth.dto.SignupRequest;
import com.prem.myfirstapp.auth.model.User;
import com.prem.myfirstapp.auth.repository.RoleRepository;
import com.prem.myfirstapp.auth.repository.UserRepository;
import com.prem.myfirstapp.common.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final VerifyEmailService verifyEmailService;

    @Transactional
    public void signup(SignupRequest req) {
        String email = req.email().trim().toLowerCase();
        if (userRepo.existsByEmailIgnoreCase(email)) {
            throw new DuplicateEmailException();
        }

        User u = new User();
        u.setFirstName(req.firstName().trim());
        u.setLastName(req.lastName().trim());
        u.setEmail(email);
        u.setMobile(req.mobile());
        u.setPasswordHash(encoder.encode(req.password()));
        u.getRoles().add(roleRepo.findByRoleName("USER").orElseThrow());
        userRepo.save(u);

        verifyEmailService.createTokenAndSendAfterCommit(u);
    }
}
