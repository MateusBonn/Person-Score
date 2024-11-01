package com.mateus_bonn.pessoa_score.repository;

import com.mateus_bonn.pessoa_score.service.person.ScoreDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoreRepository extends JpaRepository<ScoreDefinition, UUID>  {

    ScoreDefinition findByScoreInitialLessThanEqualAndScoreFinalGreaterThanEqual(Integer scoreInitial, Integer scoreFinal);
}
