package com.app.domino.controller;

import com.app.domino.context.DominoChainContext;
import com.app.domino.controller.validator.DominosValidator;
import com.app.domino.controller.validator.StartingPieceIndexValidator;
import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import com.app.domino.service.DominoChainValueCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DominoChainControllerShould {

    @Mock
    private DominoChainValueCalculator dominoChainValueCalculator;
    @Mock
    private DominosValidator dominosValidator;
    @Mock
    private StartingPieceIndexValidator startingPieceIndexValidator;
    @InjectMocks
    private DominoChainController controller;

    @Test
    public void calculateDominoChainValue() {

        // setup
        DominoChainContext context = new DominoChainContext();
        context.setDominos(buildDominos());
        int expectedValue = 3;
        when(dominoChainValueCalculator.execute(context.getDominos(), context.getStartingPieceIndex())).thenReturn(expectedValue);

        // execute
        int actualValue = controller.calculateDominoChainValue(context);

        // verify
        assertEquals(expectedValue, actualValue);
        verify(startingPieceIndexValidator).validate(context.getStartingPieceIndex(), context.getDominos().size());
        verify(dominosValidator).validate(context.getDominos());
        verify(dominoChainValueCalculator).execute(context.getDominos(), context.getStartingPieceIndex());
    }

    private List<Domino> buildDominos() {

        Domino domino1 = new Domino();
        domino1.setFront(new Face(3));
        domino1.setBack(new Face(5));

        Domino domino2 = new Domino();
        domino2.setFront(new Face(3));
        domino2.setBack(new Face(8));

        return asList(domino1, domino2);
    }
}