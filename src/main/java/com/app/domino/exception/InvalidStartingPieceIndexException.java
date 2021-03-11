package com.app.domino.exception;

/**
 * Custom exception thrown when invalid starting piece index is provided
 */
public class InvalidStartingPieceIndexException extends RuntimeException {

    public InvalidStartingPieceIndexException(String message) {
        super(message);
    }
}
