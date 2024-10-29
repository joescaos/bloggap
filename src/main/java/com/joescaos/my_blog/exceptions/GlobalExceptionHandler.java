package com.joescaos.my_blog.exceptions;

import com.joescaos.my_blog.dto.ErrorDetails;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // handle specific exceptions
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest webRequest) {
    ErrorDetails errorDetails =
        ErrorDetails.builder()
            .timestamp(LocalDate.now())
            .message(ex.getMessage())
            .details(webRequest.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
  }

  @ExceptionHandler(BlogAPIException.class)
  public ResponseEntity<ErrorDetails> handleBlogAPIException(
      BlogAPIException ex, WebRequest webRequest) {
    ErrorDetails errorDetails =
        ErrorDetails.builder()
            .timestamp(LocalDate.now())
            .message(ex.getMessage())
            .details(webRequest.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
  }

  // handle global exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest webRequest) {
    ErrorDetails errorDetails =
        ErrorDetails.builder()
            .timestamp(LocalDate.now())
            .message(ex.getMessage())
            .details(webRequest.getDescription(false))
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
  }

  //this can be done as well with ExceptionHandle with methodArgumentNotValid exception
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              errors.put(fieldName, message);
            });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDetails> handleAccessDeniedException(
          AccessDeniedException ex, WebRequest webRequest) {
    ErrorDetails errorDetails =
            ErrorDetails.builder()
                    .timestamp(LocalDate.now())
                    .message(ex.getMessage())
                    .details(webRequest.getDescription(false))
                    .build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
  }
}
