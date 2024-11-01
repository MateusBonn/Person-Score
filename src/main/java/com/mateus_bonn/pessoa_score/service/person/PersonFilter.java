package com.mateus_bonn.pessoa_score.service.person;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilter {

    private UUID id;

    private String name;

    private Long age;

    private String phone;

    private String cep;

    private String state;

    private String city;

    private String neighborhood;

    private String streetAddress;

    private String scoreDescription;
}
