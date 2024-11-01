package com.mateus_bonn.pessoa_score.service.login;

import com.mateus_bonn.pessoa_score.exception.Error;
import com.mateus_bonn.pessoa_score.exception.login.LoginException;
import com.mateus_bonn.pessoa_score.repository.LoginRepository;
import com.mateus_bonn.pessoa_score.request.login.LoginRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginUpdateRequest;
import com.mateus_bonn.pessoa_score.response.AutenticatedResponse;
import com.mateus_bonn.pessoa_score.security.TokenService;
import com.mateus_bonn.pessoa_score.utils.login.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoginService {

  final LoginRepository loginRepository;
  final LoginMapper mapper;
  final AuthenticationManager authenticationManager;

  final TokenService tokenService;

  public AutenticatedResponse login(LoginRequest loginRequest) throws LoginException {

    var usernamePassword =
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    return tokenService.generateToken((Login) auth.getPrincipal());
  }


  public Login register (Login login) {

    Boolean user = loginRepository.existsByUsername(login.getUsername());

    if(user) {
      throw new LoginException(new Error("LoginException", "Username already exists"));
    }

    login.setPassword(new BCryptPasswordEncoder().encode(login.getPassword()));
    login.setFullName(login.getFullName().toUpperCase());
    login.setUsername(login.getUsername());
    return loginRepository.save(login);
  }


  public Login updateLogin(UUID id, LoginUpdateRequest loginUpdateRequest) {
    Login login = loginRepository.findById(id).orElseThrow(() ->
            new LoginException(
                    new Error("LoginNotFound","Login not found with id" + id)));
    return loginRepository.save(mapper.loginUpdateToEntity(login, loginUpdateRequest));
  }

  public Login updateRole(UUID id, UserRole role) {
    Login login = loginRepository.findById(id).orElseThrow(() ->
            new LoginException(
                    new Error("LoginNotFound","Login not found with id" + id)));
    login.setRole(role);
    return loginRepository.save(login);
  }
}
