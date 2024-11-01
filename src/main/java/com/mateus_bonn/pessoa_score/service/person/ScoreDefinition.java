package com.mateus_bonn.pessoa_score.service.person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "score_definition")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String scoreDescription;

    private Integer scoreInitial;

    private Integer scoreFinal;
}
