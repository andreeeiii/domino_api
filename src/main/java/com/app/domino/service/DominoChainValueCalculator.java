package com.app.domino.service;

import com.app.domino.context.CalculationContext;
import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Service in charge of calculating domino chain value
 */
@Service
public class DominoChainValueCalculator {

    public int execute(List<Domino> dominos, int startingPieceIndex) {

        return calculateValue(dominos.get(startingPieceIndex), dominos, startingPieceIndex);
    }

    private int calculateValue(Domino startingDomino, List<Domino> dominos, int usedDominoIndex) {

        CalculationContext context = new CalculationContext();

        if (isNull(startingDomino)) {
            return context.getCurrentSum();
        }

        dominos.remove(usedDominoIndex);

        for (int index = 0; index < dominos.size(); index++) {
            Domino currentDomino = dominos.get(index);
            if (isMatchingDominos(startingDomino.getFront(), currentDomino.getFront())) {

                context = buildContext(startingDomino.getFront().getValue(), index,
                        createMergedDomino(startingDomino.getBack(), currentDomino.getBack()));
                break;

            } else if (isMatchingDominos(startingDomino.getFront(), currentDomino.getBack())) {

                context = buildContext(startingDomino.getFront().getValue(), index,
                        createMergedDomino(startingDomino.getBack(), currentDomino.getFront()));
                break;

            } else if (isMatchingDominos(startingDomino.getBack(), currentDomino.getFront())) {

                context = buildContext(startingDomino.getBack().getValue(), usedDominoIndex,
                        createMergedDomino(startingDomino.getFront(), currentDomino.getBack()));
                break;

            } else if (isMatchingDominos(startingDomino.getBack(), currentDomino.getBack())) {

                context = buildContext(startingDomino.getBack().getValue(), index,
                        createMergedDomino(startingDomino.getFront(), currentDomino.getFront()));
                break;
            }
        }

        if (nonNull(context.getMergedDomino())) {
            context.setCurrentSum(context.getCurrentSum() +
                    calculateValue(context.getMergedDomino(), dominos, context.getUsedDominoIndex()));
        }

        return context.getCurrentSum();
    }

    private CalculationContext buildContext(int faceValue, int index, Domino mergedDomino) {

        CalculationContext context = new CalculationContext();
        context.setCurrentSum(faceValue);
        context.setUsedDominoIndex(index);
        context.setMergedDomino(mergedDomino);

        return context;
    }

    private Domino createMergedDomino(Face frontFace, Face backFace) {

        Domino mergedDomino = new Domino();

        mergedDomino.setFront(frontFace);
        mergedDomino.setBack(backFace);

        return mergedDomino;
    }

    private boolean isMatchingDominos(Face frontFace, Face backFace) {

        return frontFace.getValue() == backFace.getValue() &&
                frontFace.getConnectedTo() == null &&
                backFace.getConnectedTo() == null;
    }
}