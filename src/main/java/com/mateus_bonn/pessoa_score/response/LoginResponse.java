package com.mateus_bonn.pessoa_score.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {

    private UUID id;

    private String cpf;

    private String fullName;

}
