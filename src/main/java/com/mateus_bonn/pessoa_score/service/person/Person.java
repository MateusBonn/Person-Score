package com.mateus_bonn.pessoa_score.service.person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private Long age;

    @Pattern(regexp = "^\\+55 \\(\\d{2}\\) 9\\d{8}$",
            message = "O número de telefone deve estar no formato: +55 (DD) 9XXXXXXXX")
    private String phone;

    private String cep;

    private String state;

    private String city;

    private String neighborhood;

    private String streetAddress;

    @Min(value = 0, message = "O score deve ser um valor entre 0 e 1000")
    private Integer score;


}
