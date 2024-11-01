package com.mateus_bonn.pessoa_score.service.person;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;

}
