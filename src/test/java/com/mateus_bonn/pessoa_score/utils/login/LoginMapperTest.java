package com.mateus_bonn.pessoa_score.utils.login;

import com.mateus_bonn.pessoa_score.request.login.LoginSaveRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginUpdateRequest;
import com.mateus_bonn.pessoa_score.response.LoginResponse;
import com.mateus_bonn.pessoa_score.service.login.Login;
import com.mateus_bonn.pessoa_score.service.login.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LoginMapperTest {

    @InjectMocks
    private LoginMapper loginMapper;

    @Test
    void testRequestSaveToEntity() {
        LoginSaveRequest loginSaveRequest = new LoginSaveRequest();
        loginSaveRequest.setUsername("testuser");
        loginSaveRequest.setPassword("password");
        loginSaveRequest.setCpf("12345678901");
        loginSaveRequest.setRole(UserRole.USER);
        loginSaveRequest.setFullName("Test User");

        Login login = loginMapper.requestSaveToEntity(loginSaveRequest);

        assertNotNull(login);
        assertEquals("testuser", login.getUsername());
        assertEquals("password", login.getPassword());
        assertEquals("12345678901", login.getCpf());
        assertEquals(UserRole.USER, login.getRole());
        assertEquals("Test User", login.getFullName());
    }

    @Test
    void testLoginUpdateToEntity() {
        Login login = new Login();
        login.setFullName("Test User");

        LoginUpdateRequest loginUpdateRequest = new LoginUpdateRequest();
        loginUpdateRequest.setFullName("Updated User");

        Login updatedLogin = loginMapper.loginUpdateToEntity(login, loginUpdateRequest);

        assertNotNull(updatedLogin);
        assertEquals("Updated User", updatedLogin.getFullName());
    }

    @Test
    void testToResponse() {
        Login login = new Login();
        login.setId(UUID.randomUUID());
        login.setCpf("12345678901");
        login.setFullName("Test User");

        LoginResponse loginResponse = loginMapper.toResponse(login);

        assertNotNull(loginResponse);
        assertEquals(login.getId(), loginResponse.getId());
        assertEquals("12345678901", loginResponse.getCpf());
        assertEquals("Test User", loginResponse.getFullName());
    }
}