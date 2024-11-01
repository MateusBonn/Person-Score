package com.mateus_bonn.pessoa_score.service.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

  ADMIN("ADMIN"),
  USER("USER");

  private final String role;
}
