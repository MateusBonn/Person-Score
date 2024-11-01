package com.mateus_bonn.pessoa_score.request.login;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  @NotNull(message = "The field username can't be null")
  private String username;

  @NotNull(message = "The field password can't be null")
  private String password;

}
