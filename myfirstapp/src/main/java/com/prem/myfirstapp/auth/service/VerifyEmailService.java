package com.prem.myfirstapp.auth.service;

import com.prem.myfirstapp.auth.model.EmailVerificationToken;
import com.prem.myfirstapp.auth.model.User;
import com.prem.myfirstapp.auth.repository.EmailVerificationTokenRepository;
import com.prem.myfirstapp.common.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class VerifyEmailService {
    private final EmailVerificationTokenRepository tokenRepo;
    private final EmailService emailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    // Call inside an existing transaction (signup).
    public void createTokenAndSendAfterCommit(User user) {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        EmailVerificationToken t = new EmailVerificationToken();
        t.setUser(user);
        t.setTokenHash(sha256(rawToken));
        t.setExpiresAt(OffsetDateTime.now().plusMinutes(30));
        tokenRepo.save(t);

        String link = frontendUrl + "/verify-email?token=" + rawToken;
        String to = user.getEmail();

        // send the mail only if the DB transaction actually commits
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                emailService.sendVerification(to, link);
            }
        });
    }

    @Transactional
    public void verify(String rawToken) {
        EmailVerificationToken t = tokenRepo.findByTokenHash(sha256(rawToken))
                .orElseThrow(() -> new InvalidTokenException("Invalid link"));

        if (t.getUsedAt() != null) throw new InvalidTokenException("Link already used");
        if (t.getExpiresAt().isBefore(OffsetDateTime.now())) throw new InvalidTokenException("Link expired");

        OffsetDateTime now = OffsetDateTime.now();
        t.setUsedAt(now);
        t.getUser().setEmailVerifiedAt(now);   // saved automatically at commit
    }

    private static String sha256(String s) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
