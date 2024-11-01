package com.mateus_bonn.pessoa_score.service.login;

import com.mateus_bonn.pessoa_score.exception.login.LoginException;
import com.mateus_bonn.pessoa_score.repository.LoginRepository;
import com.mateus_bonn.pessoa_score.request.login.LoginRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginUpdateRequest;
import com.mateus_bonn.pessoa_score.response.AutenticatedResponse;
import com.mateus_bonn.pessoa_score.security.TokenService;
import com.mateus_bonn.pessoa_score.utils.login.LoginMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class LoginServiceTest {

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private LoginMapper mapper;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() throws LoginException {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        Login login = new Login();
        login.setUsername("testuser");
        login.setPassword(new BCryptPasswordEncoder().encode("password"));

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))) 

                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(login); 

        when(tokenService.generateToken(login)).thenReturn(new AutenticatedResponse("testToken", UserRole.ADMIN, Instant.now()));

        AutenticatedResponse response = loginService.login(loginRequest);

        assertEquals("testToken", response.getAccessToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(login); 

    }

    @Test
    void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new 
                        LoginException(null)); // Simulando falha na autenticação

        assertThrows(LoginException.class, () -> loginService.login(loginRequest));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, 
                never()).generateToken(any());
    }

    @Test
    void testRegisterSuccess() {
        Login login = new Login();
        login.setUsername("newuser");
        login.setPassword("password");
        login.setFullName("New User");

        when(loginRepository.existsByUsername("newuser")).thenReturn(false);
        when(loginRepository.save(any(Login.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Login savedLogin = loginService.register(login);

        assertNotNull(savedLogin);
        assertNotEquals("password", savedLogin.getPassword());
        assertEquals("NEW USER", savedLogin.getFullName());
        verify(loginRepository).existsByUsername("newuser");
        verify(loginRepository).save(any(Login.class));
    }

    @Test
    void testRegisterFailure() {
        Login login = new Login();
        login.setUsername("existinguser");
        login.setPassword("password");
        login.setFullName("Existing User");

        when(loginRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThrows(LoginException.class, () -> loginService.register(login));
        verify(loginRepository).existsByUsername("existinguser");
        verify(loginRepository, never()).save(any(Login.class));
    }

    @Test
    void testUpdateLoginSuccess() {
        UUID loginId = UUID.randomUUID();
        LoginUpdateRequest loginUpdateRequest = new LoginUpdateRequest();
        loginUpdateRequest.setFullName("Updated User");
        Login existingLogin = new Login();
        existingLogin.setId(loginId);
        existingLogin.setFullName("Original User");
        Login newLogin = new Login();
        newLogin.setId(loginId);
        newLogin.setFullName("Updated User");


        when(loginRepository.findById(loginId)).thenReturn(Optional.of(existingLogin));
        when(mapper.loginUpdateToEntity(existingLogin,loginUpdateRequest)).thenReturn(newLogin);
        when(loginRepository.save(any(Login.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Login updatedLogin = loginService.updateLogin(loginId, loginUpdateRequest);

        assertNotNull(updatedLogin);
        assertEquals("Updated User", updatedLogin.getFullName());
        verify(loginRepository).findById(loginId);
        verify(loginRepository).save(any(Login.class));
    }

    @Test
    void testUpdateLoginNotFound() {
        UUID loginId = UUID.randomUUID();
        LoginUpdateRequest loginUpdateRequest = new LoginUpdateRequest();
        loginUpdateRequest.setFullName("Updated User");

        when(loginRepository.findById(loginId)).thenReturn(Optional.empty());

        assertThrows(LoginException.class, () -> loginService.updateLogin(loginId, loginUpdateRequest));
        verify(loginRepository).findById(loginId);
        verify(loginRepository, never()).save(any(Login.class));
    }

    @Test
    void testUpdateRoleSuccess() {
        UUID loginId = UUID.randomUUID();
        Login existingLogin = new Login();
        existingLogin.setId(loginId);
        existingLogin.setRole(UserRole.USER);

        when(loginRepository.findById(loginId)).thenReturn(Optional.of(existingLogin));
        when(loginRepository.save(any(Login.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Login updatedLogin = loginService.updateRole(loginId, UserRole.ADMIN);

        assertNotNull(updatedLogin);
        assertEquals(UserRole.ADMIN, updatedLogin.getRole());
        verify(loginRepository).findById(loginId);
        verify(loginRepository).save(any(Login.class));
    }

    @Test
    void testUpdateRoleNotFound() {
        UUID loginId = UUID.randomUUID();

        when(loginRepository.findById(loginId)).thenReturn(Optional.empty());

        assertThrows(LoginException.class, () -> loginService.updateRole(loginId, UserRole.ADMIN));
        verify(loginRepository).findById(loginId);
        verify(loginRepository, never()).save(any(Login.class));
    }
}