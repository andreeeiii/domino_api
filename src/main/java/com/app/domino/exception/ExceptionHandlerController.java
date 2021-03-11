package com.app.domino.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Service in charge of handling custom exceptions of the application
 */
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDominoException.class)
    protected ResponseEntity<Object> handleInvalidDominoException(InvalidDominoException e, WebRequest req) {

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, req);
    }

    @ExceptionHandler(InvalidStartingPieceIndexException.class)
    protected ResponseEntity<Object> handleInvalidStartingPieceIndexException(InvalidStartingPieceIndexException e, WebRequest req) {

        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, req);
    }
}