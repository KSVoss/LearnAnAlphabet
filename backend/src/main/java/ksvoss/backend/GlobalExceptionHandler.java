package ksvoss.backend;

import ksvoss.backend.models.User;
import ksvoss.backend.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException exception){

    }
}


/*
@ControllerAdvice
public class CompleteExceptionHandler {
    @ExceptionHandler(value = TodoNotFoundException.class)
    public ResponseEntity handleToDoNotFoundException(TodoNotFoundException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);

    }

*/
