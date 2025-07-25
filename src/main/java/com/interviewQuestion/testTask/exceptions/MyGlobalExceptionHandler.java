package com.interviewQuestion.testTask.exceptions;

import com.interviewQuestion.testTask.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    // Обработка ResourceNotFoundException чтобы было понятно пользователю что не так
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Было добавлено когда выкидывало данный Exception когда мы вводили не правильный id
    @ExceptionHandler(IllegalFormatConversionException.class)
    public ResponseEntity<APIResponse> myIllegalFormatConversionException(IllegalFormatConversionException e){
        APIResponse apiResponse = new APIResponse("No task with such id.", false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });

        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }
}
