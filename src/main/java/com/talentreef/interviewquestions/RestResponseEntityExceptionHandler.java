package com.talentreef.interviewquestions;

import com.talentreef.interviewquestions.exceptions.ElementAlreadyExistsException;
import com.talentreef.interviewquestions.exceptions.ElementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errorList = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map((field) -> field.getField() + ": " + field.getDefaultMessage())
        .toList();

    return ResponseEntity.badRequest().body(errorList);
  }

  @ExceptionHandler(ElementNotFoundException.class)
  public ResponseEntity<Object> handleElementNotFoundException(ElementNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ElementAlreadyExistsException.class)
  public ResponseEntity<Object> handleElementAlreadyExistsException(ElementAlreadyExistsException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
