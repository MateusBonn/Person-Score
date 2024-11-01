package com.mateus_bonn.pessoa_score.utils.login;

import com.mateus_bonn.pessoa_score.request.login.LoginSaveRequest;
import com.mateus_bonn.pessoa_score.request.login.LoginUpdateRequest;
import com.mateus_bonn.pessoa_score.response.LoginResponse;
import com.mateus_bonn.pessoa_score.service.login.Login;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    public Login requestSaveToEntity(LoginSaveRequest loginSaveRequest) {
        return Login.builder()
                .username(loginSaveRequest.getUsername())
                .password(loginSaveRequest.getPassword())
                .cpf(loginSaveRequest.getCpf())
                .role(loginSaveRequest.getRole())
                .fullName(loginSaveRequest.getFullName())
                .build();
    }

    public Login loginUpdateToEntity(Login login, LoginUpdateRequest loginUpdateRequest) {
        login.setFullName(loginUpdateRequest.getFullName());
        return login;
    }

    public LoginResponse toResponse(Login login) {
       return LoginResponse.builder()
               .id(login.getId())
               .cpf(login.getCpf())
               .fullName(login.getFullName())
               .build();
    }
}
