package com.mateus_bonn.pessoa_score.service.login;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "login")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String cpf;

  private String fullName;

  private String username;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.getRole() == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_USER"));
    else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
