package com.app.domino.controller.validator;

import com.app.domino.exception.InvalidDominoException;
import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Service in charge of validating domino pieces
 */
@Service
public class DominosValidator {

    private final static String PIECE_VALUE_ERROR_MESSAGE = "Domino piece must have a %s value ranging from 1 to 10";

    public void validate(List<Domino> dominos) {

        dominos.forEach(domino -> {
            validateDominoFace(domino.getBack(), String.format(PIECE_VALUE_ERROR_MESSAGE, "back"));
            validateDominoFace(domino.getFront(), String.format(PIECE_VALUE_ERROR_MESSAGE, "front"));
            validateDominoFaces(domino.getBack(), domino.getFront());
        });
    }

    private void validateDominoFace(Face face, String errorMessage) {

        if (isNull(face) || face.getValue() < 1 || face.getValue() > 10) {
            throw new InvalidDominoException(errorMessage);
        }
        if (nonNull(face.getConnectedTo())) {
            throw new InvalidDominoException("Domino piece cannot be connected to another piece");
        }
    }

    private void validateDominoFaces(Face back, Face front) {

        if (nonNull(back) && nonNull(front) && back.getValue() != 0 && front.getValue() != 0
                && back.getValue() == front.getValue()) {
            throw new InvalidDominoException("Domino piece cannot have the same value for both faces");
        }
    }
}
