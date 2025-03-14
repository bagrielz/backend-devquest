package br.com.devquest.api.exceptions.handlers;

import br.com.devquest.api.exceptions.InvalidJwtAuthenticationException;
import br.com.devquest.api.exceptions.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Hidden
@ControllerAdvice
@RestController
public class CustomizedExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> usernameNotFoundException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidJwtAuthenticationException.class)
  public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = createExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  private static ExceptionResponse createExceptionResponse(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
            .timestamp(new Date())
            .message(ex.getMessage())
            .details(request.getDescription(false))
            .build();
    return exceptionResponse;
  }

}
