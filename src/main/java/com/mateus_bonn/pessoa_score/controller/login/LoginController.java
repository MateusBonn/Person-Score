package com.mateus_bonn.pessoa_score.controller.login;


import com.mateus_bonn.pessoa_score.request.login.LoginRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginSaveRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginUpdateRequest;
import com.mateus_bonn.pessoa_score.response.AutenticatedResponse;
import com.mateus_bonn.pessoa_score.response.LoginResponse;
import com.mateus_bonn.pessoa_score.service.login.Login;
import com.mateus_bonn.pessoa_score.service.login.LoginService;
import com.mateus_bonn.pessoa_score.service.login.UserRole;
import com.mateus_bonn.pessoa_score.utils.login.LoginMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "API-Login")
public class LoginController {

  final LoginService loginService;
  final LoginMapper mapper;

  @Operation(summary = "Efetua o login para autenticação na API", method = "POST")
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public AutenticatedResponse login(@RequestBody @Valid LoginRequest request) {
    return loginService.login(request);
  }

  @Operation(summary = "Registra login de usuário", method = "POST")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResponse register(@RequestBody @Valid LoginSaveRequest loginSaveRequest){
    Login login = loginService.register(mapper.requestSaveToEntity(loginSaveRequest));
    return mapper.toResponse(login);
  }

  @Operation(summary = "Altera dados do login de usuário", method = "PATCH")
  @PatchMapping("/{id}")
  public LoginResponse update(@PathVariable UUID id, @RequestBody @Valid LoginUpdateRequest loginUpdateRequest){
    Login login = loginService.updateLogin(id, loginUpdateRequest);
    return mapper.toResponse(login);
  }

  @Operation(summary = "Altera nivel de permissão do usuário", method = "PATCH")
  @PatchMapping("/{id}/role/{role}")
  public LoginResponse updateRole(@PathVariable UUID id, @PathVariable UserRole role){
    Login login = loginService.updateRole(id, role);
      return mapper.toResponse(login);
  }
}
