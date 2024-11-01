package com.mateus_bonn.pessoa_score.service.person;

import com.mateus_bonn.pessoa_score.ConsumerAPI;
import com.mateus_bonn.pessoa_score.exception.Error;
import com.mateus_bonn.pessoa_score.exception.person.PersonException;
import com.mateus_bonn.pessoa_score.repository.PersonRepository;
import com.mateus_bonn.pessoa_score.utils.PageFilter;
import com.mateus_bonn.pessoa_score.utils.person.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private static final String URL_API_CEP = "https://brasilapi.com.br/api/cep/v1/%s";

    final PersonRepository repository;
    final ConsumerAPI<Address> restAPI;
    final PersonMapper mapper;

    public Person savePerson(Person person) {

        Address address = restAPI.consumerAPIAddress(URL_API_CEP, person.getCep());
        return repository.save(mapper.toEntity(person, address));

    }

    public Person updatePerson(UUID id, Person person) {
        Person entity = repository.findById(id).orElseThrow(() ->
                new PersonException(
                        new Error("PersonNotFound","Person not found with id" + id)));
        person.setId(entity.getId());
        return repository.save(person);
    }

    public Page<PersonFilter> searchPerson(PageFilter pageFilter, List<String> searchBy) {
        var response = repository.findAll(pageFilter, searchBy);

        if (response.isEmpty()){
            throw new PersonException( new Error("PersonNotDataFound","No data found" ));
        }

        var count = repository.count(pageFilter, searchBy);

        return new PageImpl<>(response, pageFilter.toPageable(), count);
    }
}
