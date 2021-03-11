package com.app.domino.exception;

/**
 * Custom exception thrown when an invalid domino is provided
 */
public class InvalidDominoException extends RuntimeException {

    public InvalidDominoException(String message) {
        super(message);
    }
}
