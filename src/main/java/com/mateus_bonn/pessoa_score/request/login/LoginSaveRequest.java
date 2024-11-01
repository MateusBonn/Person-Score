package com.mateus_bonn.pessoa_score.request.login;

import com.mateus_bonn.pessoa_score.service.login.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginSaveRequest {

  @CPF
  @NotNull(message = "CPF can't be null")
  private String cpf;

  @NotNull(message = "The field fullname can't be null")
  private String fullName;

  @NotNull(message = "The field username can't be null")
  private String username;

  @NotNull(message = "The field password can't be null")
  private String password;

  @NotNull(message = "Role can't be null")
  private UserRole role;

}
