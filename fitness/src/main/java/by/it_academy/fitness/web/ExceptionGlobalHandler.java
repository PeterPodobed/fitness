package by.it_academy.fitness.web;

import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(value = {MultipleErrorResponse.class})
    public ResponseEntity<MultipleErrorResponse> MultipleExHandle(MultipleErrorResponse e) {
        return ResponseEntity.status(400).body(e);
    }

    @ExceptionHandler(value = {SingleErrorResponse.class})
    public ResponseEntity<SingleErrorResponse> SingleExHandle(SingleErrorResponse e) {
        return ResponseEntity.status(400).body(e);
    }

}
