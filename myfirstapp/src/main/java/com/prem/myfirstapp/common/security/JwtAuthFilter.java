package com.prem.myfirstapp.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                Claims c = jwt.parse(header.substring(7));
                List<?> roles = c.get("roles", List.class);
                var authorities = roles.stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList();
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(c.getSubject(), null, authorities));
// debugging jwt
                    var authentication =
        new UsernamePasswordAuthenticationToken(
                c.getSubject(),
                null,
                authorities
        );

SecurityContextHolder.getContext().setAuthentication(authentication);

// Temporary debugging
System.out.println("========== JWT DEBUG ==========");
System.out.println("Subject: " + c.getSubject());
System.out.println("JWT Roles: " + roles);
System.out.println("Spring Authorities: " + authorities);
System.out.println("Authenticated: " + authentication.isAuthenticated());
System.out.println("================================");

            } catch (JwtException | IllegalArgumentException e) {
                // invalid or expired token: stay unauthenticated
            }
        }
        chain.doFilter(req, res);
    }
}
