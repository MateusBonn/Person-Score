package com.mateus_bonn.pessoa_score.utils.person;

import com.mateus_bonn.pessoa_score.request.person.PersonRequest;
import com.mateus_bonn.pessoa_score.request.person.PersonUpdateRequest;
import com.mateus_bonn.pessoa_score.service.person.Address;
import com.mateus_bonn.pessoa_score.service.person.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(Person person, Address address) {
        person.setCep(address.getCep());
        person.setState(address.getState());
        person.setCity(address.getCity());
        person.setNeighborhood(address.getNeighborhood());
        person.setStreetAddress(address.getStreet());
        return person;

    }

    public Person toEntity(PersonRequest personRequest) {
        return Person.builder()
                .name(personRequest.getName())
                .age(personRequest.getAge())
                .phone(personRequest.getPhone())
                .score(personRequest.getScore())
                .cep(personRequest.getCep())
                .build();
    }

    public Person toEntity(PersonUpdateRequest personUpdateRequest) {
        return Person.builder()
                .name(personUpdateRequest.getName())
                .age(personUpdateRequest.getAge())
                .phone(personUpdateRequest.getPhone())
                .score(personUpdateRequest.getScore())
                .cep(personUpdateRequest.getCep())
                .state(personUpdateRequest.getState())
                .city(personUpdateRequest.getCity())
                .neighborhood(personUpdateRequest.getNeighborhood())
                .streetAddress(personUpdateRequest.getPublicPlace())
                .build();
    }


}
