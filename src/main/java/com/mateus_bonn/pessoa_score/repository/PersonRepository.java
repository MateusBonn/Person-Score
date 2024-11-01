package com.mateus_bonn.pessoa_score.repository;

import com.mateus_bonn.pessoa_score.service.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID>, PersonCustomRepository {
}
