package lt.techin.demo.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;


import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.time.LocalTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

    // Serializes to JSON, using Jackson
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(ConstraintViolationException ex) {

    // Serializes to JSON, using Jackson
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      errors.put(violation.getPropertyPath().toString(),
              violation.getMessage());
    });


    return ResponseEntity.badRequest().body(errors);
  }

}
