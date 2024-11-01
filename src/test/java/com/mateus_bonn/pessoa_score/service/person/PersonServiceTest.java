package com.mateus_bonn.pessoa_score.service.person;

import com.mateus_bonn.pessoa_score.ConsumerAPI;
import com.mateus_bonn.pessoa_score.exception.person.PersonException;
import com.mateus_bonn.pessoa_score.repository.PersonRepository;
import com.mateus_bonn.pessoa_score.utils.PageFilter;
import com.mateus_bonn.pessoa_score.utils.person.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private ConsumerAPI<Address> restAPI;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePerson() {
        // Arrange
        Person person = new Person();
        person.setCep("12345678");
        Address address = new Address();
        when(restAPI.consumerAPIAddress(anyString(), anyString())).thenReturn(address);
        when(mapper.toEntity(person, address)).thenReturn(person);
        when(repository.save(person)).thenReturn(person);

        Person savedPerson = service.savePerson(person);

        assertNotNull(savedPerson);
        verify(restAPI, times(1)).consumerAPIAddress(anyString(), anyString());
        verify(mapper, times(1)).toEntity(person, address);
        verify(repository, times(1)).save(person);
    }

    @Test
    void testUpdatePerson() {
        UUID id = UUID.randomUUID();
        Person person = new Person();
        person.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(person);

        Person updatedPerson = service.updatePerson(id, person);

        assertNotNull(updatedPerson);
        assertEquals(id, updatedPerson.getId());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(person);
    }

    @Test
    void testUpdatePersonNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PersonException.class, () -> service.updatePerson(id, new Person()));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void testSearchPerson() {
        PageFilter pageFilter = new PageFilter();
        List<String> searchBy = Collections.singletonList("name");
        List<PersonFilter> personFilters = Collections.singletonList(new PersonFilter());
        Page<PersonFilter> expectedPage = new PageImpl<>(personFilters, pageFilter.toPageable(), 1);
        when(repository.findAll(pageFilter, searchBy)).thenReturn(personFilters);
        when(repository.count(pageFilter, searchBy)).thenReturn(1L);

        Page<PersonFilter> resultPage = service.searchPerson(pageFilter, searchBy);

        assertEquals(expectedPage, resultPage);
        verify(repository, times(1)).findAll(pageFilter, searchBy);
        verify(repository, times(1)).count(pageFilter, searchBy);
    }

    @Test
    void testSearchPersonNoDataFound() {
        PageFilter pageFilter = new PageFilter();
        List<String> searchBy = Collections.singletonList("name");
        when(repository.findAll(pageFilter, searchBy)).thenReturn(Collections.emptyList());

        assertThrows(PersonException.class, () -> service.searchPerson(pageFilter, searchBy));
        verify(repository, times(1)).findAll(pageFilter, searchBy);
        verify(repository, never()).count(any(), any());
    }
}