package com.mateus_bonn.pessoa_score.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mateus_bonn.pessoa_score.response.AutenticatedResponse;
import com.mateus_bonn.pessoa_score.service.login.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;



@Service
@RequiredArgsConstructor
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.expiration}")
  private long expirationToken;



  public AutenticatedResponse generateToken(Login login){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      var expirationDateToken = generateExpirationDate(expirationToken);
      var token = JWT.create()
              .withIssuer("auth-api")
              .withSubject(login.getUsername())
              .withExpiresAt(expirationDateToken)
              .sign(algorithm);

      return AutenticatedResponse.builder()
              .accessToken(token)
              .role(login.getRole())
              .expirationDateAccessToken(expirationDateToken)
              .build();

    }catch (JWTCreationException exception){
      throw new RuntimeException("Error while generating token " + exception);
    }
  }

  public String validateToken(String token) throws JWTVerificationException {

    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm)
            .withIssuer("auth-api")
            .build()
            .verify(token)
            .getSubject();
  }



  private Instant generateExpirationDate(long expiration) {

    return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
  }


}
