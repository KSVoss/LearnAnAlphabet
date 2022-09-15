package ksvoss.backend;


import ksvoss.backend.exeptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {


    @ExceptionHandler(value = CombinationLetterInAnAlphabetException.class)
    public ResponseEntity handleCombinationLetterInAnAlphabetException(CombinationLetterInAnAlphabetException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","alphabet or combination alphabet and letter does not exist");
        return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value  = EmptyAlphabetDatabaseException.class)
    public ResponseEntity handleEmptyAlphabetDatabaseException(EmptyAlphabetDatabaseException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","there are no alphabets in the database");
        return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = SelectedAlphabetIsNullException.class)
    public ResponseEntity handleSelectedAlphabetIsNullException(SelectedAlphabetIsNullException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","the selected alphabetId is null");
        return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","the combination of mailadress and password was not found");
        return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TooFewLettersSelectedException.class)
    public ResponseEntity handleTooFewLettersSelectedException(TooFewLettersSelectedException exception){
        Map<String, Object>  responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp",LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","in the selected alphabet two or more elements have to be selected");
        return new ResponseEntity<>(responseBody,HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(value= UserByIdNotFoundExeption.class)
    public ResponseEntity handleUserByIdNotFoundExeption(UserByIdNotFoundExeption exeption){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp",LocalDateTime.now());
        responseBody.put("message",exeption.getMessage());
        responseBody.put("description","the userid in the pass is unknown");
        return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value= DoubleRegistrationException.class)
    public ResponseEntity handleDoubleRegistrationExeption(DoubleRegistrationException exception){
        Map<String, Object> responseBody=new LinkedHashMap<>();
        responseBody.put("timestamp",LocalDateTime.now());
        responseBody.put("message",exception.getMessage());
        responseBody.put("description","a user is already registered under this mailadress");
        return new ResponseEntity<>(responseBody,HttpStatus.CONFLICT);
    }



}


