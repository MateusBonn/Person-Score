package com.mateus_bonn.pessoa_score.controller.person;

import com.mateus_bonn.pessoa_score.request.person.PersonRequest;
import com.mateus_bonn.pessoa_score.request.person.PersonUpdateRequest;
import com.mateus_bonn.pessoa_score.service.person.Person;
import com.mateus_bonn.pessoa_score.service.person.PersonFilter;
import com.mateus_bonn.pessoa_score.service.person.PersonService;
import com.mateus_bonn.pessoa_score.utils.PageFilter;
import com.mateus_bonn.pessoa_score.utils.person.PersonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/person")
@Tag(name = "Gerenciamento de pessoa")
public class PersonController {

    final PersonService service;
    final PersonMapper mapper;

    @Operation(summary = "Registra pessoa com score", method = "POST")
    @PostMapping
    public Person savePerson(@RequestBody @Valid PersonRequest personRequest) {
        return service.savePerson(mapper.toEntity(personRequest));
    }

    @Operation(summary = "Altera dados de pessoa", method = "PUT")
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable UUID ID, @RequestBody @Valid PersonUpdateRequest personUpdateRequest) {
        return service.updatePerson(ID, mapper.toEntity(personUpdateRequest));
    }

    @Operation(summary = "Retorna dados de pessoa de acordo com filtros.", method = "GET")
    @Parameter(name = "searchBy", description = "Nome do filtro")
    @GetMapping
    public Page<PersonFilter> getPersonByFilter(@ParameterObject @Valid PageFilter pageFilter,
                                                @RequestParam(value = "search", required = false) String search,
                                                @RequestParam(value = "searchBy", required = false) List<String> searchBy) {
        return service.searchPerson(pageFilter, searchBy);
    }


}
