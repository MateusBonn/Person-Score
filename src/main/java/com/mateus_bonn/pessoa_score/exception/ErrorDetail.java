package com.mateus_bonn.pessoa_score.exception;

import java.io.Serializable;


public class ErrorDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String code;
  private final String message;
  private String target;

  public ErrorDetail(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public ErrorDetail(String code, String message, String target) {
    this(code, message);
    this.target = target;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getTarget() {
    return target;
  }

}
