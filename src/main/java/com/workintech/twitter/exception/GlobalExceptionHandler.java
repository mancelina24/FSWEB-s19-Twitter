package com.workintech.twitter.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(TwitterException ex){
                TwitterErrorResponse error=new TwitterErrorResponse(
                        ex.getMessage(),
                        ex.getHttpStatus().value(),
                        System.currentTimeMillis()
                         //LocalDateTime.now()
                );
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TwitterErrorResponse> handleException(Exception ex){
        TwitterErrorResponse error=new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis()
              //LocalDateTime.now()
                );
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }


}
