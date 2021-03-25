package com.app.domino.service;

import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DominoChainValueCalculatorShould {

    private final DominoChainValueCalculator calculator = new DominoChainValueCalculator();

    @Test
    public void calculateHighestValueForGivenDominos() {

        // setup
        List<Domino> dominos = buildDominos();
        int expectedValue = 6;

        // execute
        int actualValue = calculator.execute(dominos, 0);

        // verify
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void returnZeroForInvalidStartingDomino() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        dominos.add(null);

        // execute
        int actualValue = calculator.execute(dominos, 0);

        // verify
        assertEquals(0, actualValue);
    }

    @Test
    public void returnZeroIfNoDominoChainIsPossible() {

        // setup
        List<Domino> dominos = new ArrayList<>();
        Domino domino1 = new Domino();
        domino1.setFront(new Face(1));
        domino1.setBack(new Face(2));
        Domino domino2 = new Domino();
        domino2.setFront(new Face(3));
        domino2.setBack(new Face(4));
        dominos.add(domino1);
        dominos.add(domino2);

        // execute
        int actualValue = calculator.execute(dominos, 0);

        // verify
        assertEquals(0, actualValue);
    }

    private List<Domino> buildDominos() {

        List<Domino> dominos = new ArrayList<>();

        Domino domino1 = new Domino();
        domino1.setFront(new Face(1));
        domino1.setBack(new Face(2));

        Domino domino2 = new Domino();
        domino2.setFront(new Face(2));
        domino2.setBack(new Face(3));

        Domino domino3 = new Domino();
        domino3.setFront(new Face(2));
        domino3.setBack(new Face(4));

        Domino domino4 = new Domino();
        domino4.setFront(new Face(5));
        domino4.setBack(new Face(4));

        dominos.add(domino1);
        dominos.add(domino2);
        dominos.add(domino3);
        dominos.add(domino4);

        return dominos;
    }
}