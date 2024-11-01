package com.mateus_bonn.pessoa_score.service.person;

import com.mateus_bonn.pessoa_score.ConsumerAPI;
import com.mateus_bonn.pessoa_score.exception.Error;
import com.mateus_bonn.pessoa_score.exception.person.PersonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ConsumerCepAPI implements ConsumerAPI<Address>{

    final RestTemplate restTemplate;

    public Address consumerAPIAddress(String url, String cep) {
        try {
            ResponseEntity<Address> response = restTemplate.getForEntity(String.format(url, cep), Address.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new PersonException(new Error("CEPNOTFOUND", "Address with this CEP wasn't found: " + cep));
        }
    }

}
