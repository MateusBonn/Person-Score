package com.mateus_bonn.pessoa_score.exception.login;


import com.mateus_bonn.pessoa_score.exception.BusinessException;
import com.mateus_bonn.pessoa_score.exception.Error;

public class LoginException extends BusinessException {

  public LoginException(Error error) {
    super(error);
  }

  public LoginException(String code, String message) {
    super(new Error(code, message));
  }
}
