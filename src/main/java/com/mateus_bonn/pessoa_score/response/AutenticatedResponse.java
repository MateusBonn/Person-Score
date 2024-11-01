package com.mateus_bonn.pessoa_score.response;


import com.mateus_bonn.pessoa_score.service.login.UserRole;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AutenticatedResponse implements Serializable {

  public String accessToken;
  public UserRole role;
  public Instant expirationDateAccessToken;


}
