package com.app.domino.controller.validator;

import com.app.domino.exception.InvalidDominoException;
import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DominosValidatorShould {

    private final DominosValidator validator = new DominosValidator();

    @Test
    public void throwInvalidDominoExceptionIfFrontFaceIsNull() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(null);
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfFrontFaceValueIsBelowOne() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(0));
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfFrontFaceValueIsAboveTen() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(11));
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfFrontFaceIsConnectedToAnotherFace() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        Face frontFace = new Face(5);
        frontFace.setConnectedTo(new Face());
        domino.setFront(frontFace);
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfBackFaceIsNull() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(1));
        domino.setBack(null);
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfBackFaceValueIsBelowOne() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(1));
        domino.setBack(new Face(0));
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfBackFaceValueIsAboveTen() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(1));
        domino.setBack(new Face(11));
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfBackFaceIsConnectedToAnotherFace() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(5));
        Face backFace = new Face(3);
        backFace.setConnectedTo(new Face());
        domino.setBack(backFace);
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void throwInvalidDominoExceptionIfBothFacesHaveTheSameValue() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(5));
        domino.setBack(new Face(5));
        dominos.add(domino);

        // execute & verify
        assertThrows(InvalidDominoException.class, () -> validator.validate(dominos));
    }

    @Test
    public void notThrowException() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino = new Domino();
        domino.setFront(new Face(5));
        domino.setBack(new Face(3));
        dominos.add(domino);

        // execute
        validator.validate(dominos);
    }
}