package dev.mananhemani.markethub.Exceptions;

import dev.mananhemani.markethub.DTOs.Exception.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException e){
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiException(ApiException e){
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
