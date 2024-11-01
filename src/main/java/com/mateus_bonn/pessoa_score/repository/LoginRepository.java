package com.mateus_bonn.pessoa_score.repository;

import com.mateus_bonn.pessoa_score.service.login.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<Login, UUID> {
  UserDetails findByUsername(String username);
  Boolean existsByUsername(String username);
}
