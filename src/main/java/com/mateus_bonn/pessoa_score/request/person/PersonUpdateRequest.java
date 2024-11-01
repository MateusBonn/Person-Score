package com.mateus_bonn.pessoa_score.request.person;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonUpdateRequest {

    private String name;

    private Long age;

    @Pattern(regexp = "^\\+55 \\(\\d{2}\\) 9\\d{8}$",
            message = "O número de telefone deve estar no formato: +55 (DD) 9XXXXXXXX")
    private String phone;

    private String cep;

    private String state;

    private String city;

    private String neighborhood;

    private String publicPlace;

    @Min(value = 0, message = "O score deve ser um valor entre 0 e 1000")
    private Integer score;
}
