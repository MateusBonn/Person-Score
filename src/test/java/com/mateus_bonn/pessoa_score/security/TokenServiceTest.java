package com.mateus_bonn.pessoa_score.security;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mateus_bonn.pessoa_score.response.AutenticatedResponse;
import com.mateus_bonn.pessoa_score.service.login.Login;
import com.mateus_bonn.pessoa_score.service.login.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret");
        ReflectionTestUtils.setField(tokenService, "expirationToken", 24L);
    }

    @Test
    void testGenerateToken() {
        Login login = new Login();
        login.setUsername("testuser");
        login.setPassword("password");
        login.setRole(UserRole.USER);

        AutenticatedResponse response = tokenService.generateToken(login);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals(UserRole.USER, response.getRole());
        assertNotNull(response.getExpirationDateAccessToken());
    }

    @Test
    void testValidateToken() throws JWTVerificationException {
        Login login = new Login();
        login.setUsername("testuser");
        login.setPassword("password");
        login.setRole(UserRole.USER);

        String token = tokenService.generateToken(login).getAccessToken();

        String subject = tokenService.validateToken(token);

        assertEquals("testuser", subject);
    }

    @Test
    void testValidateTokenInvalid() {
        String token = "invalid-token";

        assertThrows(JWTVerificationException.class, () -> tokenService.validateToken(token));
    }

}