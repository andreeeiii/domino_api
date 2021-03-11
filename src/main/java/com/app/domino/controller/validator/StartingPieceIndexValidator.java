package com.app.domino.controller.validator;

import com.app.domino.exception.InvalidStartingPieceIndexException;
import org.springframework.stereotype.Service;

/**
 * Service in charge of validating starting piece index
 */
@Service
public class StartingPieceIndexValidator {

    public void validate(int startingPieceIndex, int dominosSize) {

        if (startingPieceIndex < 0 || startingPieceIndex > dominosSize - 1) {
            throw new InvalidStartingPieceIndexException("Starting piece index must have values between 0 and size of " +
                    "domino list - 1");
        }
    }
}
