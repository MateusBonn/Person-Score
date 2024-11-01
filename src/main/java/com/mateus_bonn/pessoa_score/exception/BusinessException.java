package com.mateus_bonn.pessoa_score.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private static final String CODE = "BusinessException";

  private final Error error;

  public BusinessException(Error error) {
    this.error = error;
  }

  public BusinessException(String message) {
    super(message);
    this.error = new Error(CODE, message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
    this.error = new Error(CODE, message);
  }

  public Error getError() {
    return error;
  }
}
