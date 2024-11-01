package com.mateus_bonn.pessoa_score.security;

import com.mateus_bonn.pessoa_score.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  final LoginRepository loginRepository;
  final TokenService tokenService;
  final UserDetailsServiceImpl userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                    auth
                            .requestMatchers("/v1/api-docs/**",
                                    "/v3/api-docs/**",
                                    "/v2/api-docs/**",
                                    "/configuration/ui",
                                    "/swagger-resources/**",
                                    "/configuration/security",
                                    "/swagger-ui.html",
                                    "/webjars/**",
                                    "/h2-console/**",
                                    "/h2-console",
                                    "/swagger-ui/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                            .requestMatchers(HttpMethod.POST,"/person").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/person").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST,"/auth/register").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH,"/auth").hasRole("ADMIN")
                            .anyRequest().authenticated())
            .addFilterBefore(securityFilter(), AnonymousAuthenticationFilter.class)
            .build();
  }

  @Bean
  public SecurityFilter securityFilter() {
    return new SecurityFilter(tokenService, userDetailsService);
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/v1/api-docs/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/h2-console/**",
            "/h2-console",
            "/swagger-ui/**");
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }


}
