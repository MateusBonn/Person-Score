package com.mateus_bonn.pessoa_score.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Error implements Serializable {

  private static final long serialVersionUID = 1L;

  private String code;
  private String message;
  private String target;
  private final List<ErrorDetail> details = new ArrayList<>();

  public Error() {
    this("Internal Server Error");
  }

  public Error(String message) {
    this("InternalServerError", message);
  }

  public Error(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public Error addDetail(String code, String message) {
    details.add(new ErrorDetail(code, message));
    return this;
  }

  public Error addDetail(String code, String message, String target) {
    details.add(new ErrorDetail(code, message, target));
    return this;
  }

  public Error addDetails(List<ErrorDetail> details) {
    this.details.addAll(details);
    return this;
  }

  public List<ErrorDetail> getDetails() {
    return Collections.unmodifiableList(details);
  }


}
