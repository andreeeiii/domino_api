package com.app.domino.controller.validator;

import com.app.domino.exception.InvalidStartingPieceIndexException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartingPieceIndexValidatorShould {

    private final StartingPieceIndexValidator validator = new StartingPieceIndexValidator();

    @Test
    public void throwExceptionWhenIndexValueIsBelowZero() {

        // execute & verify
        assertThrows(InvalidStartingPieceIndexException.class, () -> validator.validate(-1, 5));
    }

    @Test
    public void throwExceptionWhenIndexValueIsHigherThanDominoListSize() {

        // execute & verify
        assertThrows(InvalidStartingPieceIndexException.class, () -> validator.validate(6, 5));
    }

    @Test
    public void notThrowExceptionWhenIndexIsCorrectValue() {

        // execute
        validator.validate(0, 5);
    }
}