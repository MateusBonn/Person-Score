package com.mateus_bonn.pessoa_score.utils.person;

import com.mateus_bonn.pessoa_score.request.person.PersonRequest;
import com.mateus_bonn.pessoa_score.request.person.PersonUpdateRequest;
import com.mateus_bonn.pessoa_score.service.person.Address;
import com.mateus_bonn.pessoa_score.service.person.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class PersonMapperTest {

    @InjectMocks
    private PersonMapper personMapper;

    @Test
    void testToEntity_person_address() {
        Person person = new Person();
        Address address = new Address();
        address.setCep("12345678");
        address.setState("SP");
        address.setCity("São Paulo");
        address.setNeighborhood("Centro");
        address.setStreet("Rua A");

        Person result = personMapper.toEntity(person, address);

        assertEquals("12345678", result.getCep());
        assertEquals("SP", result.getState());
        assertEquals("São Paulo", result.getCity());
        assertEquals("Centro", result.getNeighborhood());
        assertEquals("Rua A", result.getStreetAddress());
    }

    @Test
    void testToEntity_personRequest() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setName("John Doe");
        personRequest.setAge(30L);
        personRequest.setPhone("1234567890");
        personRequest.setScore(100);
        personRequest.setCep("12345678");

        Person result = personMapper.toEntity(personRequest);

        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("1234567890", result.getPhone());
        assertEquals(100, result.getScore());
        assertEquals("12345678", result.getCep());
    }

    @Test
    void testToEntity_personUpdateRequest() {
        PersonUpdateRequest personUpdateRequest = getPersonUpdateRequest();
        Person result = personMapper.toEntity(personUpdateRequest);

        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("1234567890", result.getPhone());
        assertEquals(100, result.getScore());
        assertEquals("12345678", result.getCep());
        assertEquals("SP", result.getState());
        assertEquals("São Paulo", result.getCity());
        assertEquals("Centro", result.getNeighborhood());
        assertEquals("Rua A", result.getStreetAddress());
    }

    private PersonUpdateRequest getPersonUpdateRequest() {
        PersonUpdateRequest personUpdateRequest = new PersonUpdateRequest();
        personUpdateRequest.setName("John Doe");
        personUpdateRequest.setAge(30L);
        personUpdateRequest.setPhone("1234567890");
        personUpdateRequest.setScore(100);
        personUpdateRequest.setCep("12345678");
        personUpdateRequest.setState("SP");
        personUpdateRequest.setCity("São Paulo");
        personUpdateRequest.setNeighborhood("Centro");
        personUpdateRequest.setPublicPlace("Rua A");
        return personUpdateRequest;
    }
}