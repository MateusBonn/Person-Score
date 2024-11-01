package com.mateus_bonn.pessoa_score;

import com.mateus_bonn.pessoa_score.repository.LoginRepository;
import com.mateus_bonn.pessoa_score.repository.ScoreRepository;
import com.mateus_bonn.pessoa_score.service.login.Login;
import com.mateus_bonn.pessoa_score.service.login.UserRole;
import com.mateus_bonn.pessoa_score.service.person.ScoreDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InsertInitializer {

    private final LoginRepository loginRepository;
    private final ScoreRepository scoreRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void insertFirstLogin() {
        Login login = Login.builder()
                .cpf("03126094007")
                .username("MATEUS.FERREIRA")
                .fullName("MATEUS BONN FERREIRA")
                .password(new BCryptPasswordEncoder().encode("mat?050813"))
                .role(UserRole.ADMIN)
                .build();
        loginRepository.save(login);
        System.out.println("login salvo " + login.getUsername());
        System.out.println("login salvo " + login.getPassword());
        List<ScoreDefinition> scoreDefinitions = new ArrayList<>();
        scoreDefinitions.
                add(ScoreDefinition.builder()
                        .scoreDescription("Insuficiente")
                        .scoreInitial(0)
                        .scoreFinal(200)
                        .build());
        scoreDefinitions.
                add(ScoreDefinition.builder()
                        .scoreDescription("Inaceitável")
                        .scoreInitial(201)
                        .scoreFinal(500)
                        .build());
        scoreDefinitions.
                add(ScoreDefinition.builder()
                        .scoreDescription("Aceitável ")
                        .scoreInitial(501)
                        .scoreFinal(700)
                        .build());
        scoreDefinitions.
                add(ScoreDefinition.builder()
                        .scoreDescription("Recomendável")
                        .scoreInitial(701)
                        .scoreFinal(1000)
                        .build());

            scoreRepository.saveAll(scoreDefinitions);
    }
}
