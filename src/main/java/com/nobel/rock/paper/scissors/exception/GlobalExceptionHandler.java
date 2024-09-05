package com.nobel.rock.paper.scissors.exception;

import com.nobel.rock.paper.scissors.config.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all controllers.
 * This class handles exceptions thrown by any controller in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles GameNotFoundException and returns a 404 status code.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity containing the error details.
     */
    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.NOT_FOUND.value());
        body.put(Constants.ERROR, "Not Found");
        body.put(Constants.MESSAGE, ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles GameFinishedException and returns a 400 status code.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity containing the error details.
     */
    @ExceptionHandler(GameFinishedException.class)
    public ResponseEntity<Object> handleGameFinishedException(GameFinishedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(Constants.ERROR, "Bad Request");
        body.put(Constants.MESSAGE, ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and returns a 500 status code.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity containing the error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException() {
        Map<String, Object> body = new HashMap<>();
        body.put(Constants.TIMESTAMP, LocalDateTime.now());
        body.put(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(Constants.ERROR, "Internal Server Error");
        body.put(Constants.MESSAGE, "An unexpected error occurred");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

