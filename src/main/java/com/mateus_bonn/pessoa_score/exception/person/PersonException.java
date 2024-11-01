package com.mateus_bonn.pessoa_score.exception.person;

import com.mateus_bonn.pessoa_score.exception.BusinessException;
import com.mateus_bonn.pessoa_score.exception.Error;

public class PersonException extends BusinessException {

    public PersonException(Error error) {
        super(error);
    }

    public PersonException(String code, String message) {
        super(new Error(code, message));
    }
}
