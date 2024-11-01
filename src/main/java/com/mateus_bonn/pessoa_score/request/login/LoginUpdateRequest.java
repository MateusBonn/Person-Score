package com.mateus_bonn.pessoa_score.request.login;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUpdateRequest {

    @NotNull(message = "The field fullname can't be null")
    private String fullName;

}
