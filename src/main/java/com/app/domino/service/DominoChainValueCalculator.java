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

        //return calculateValue(dominos.get(startingPieceIndex), dominos, startingPieceIndex);

        return executeNonRecursive(dominos, startingPieceIndex);
    }

    public int executeNonRecursive(List<Domino> dominos, int startingPieceIndex) {

        int dominosSize = dominos.size();
        int highestSum = 0;

        // iterate through all the pieces
        for (int firstIndex = 0; firstIndex < dominosSize; firstIndex++) {

            int currentSum = 0;

            if (firstIndex == startingPieceIndex) {
                continue;
            }

            Domino dominoToCompare = dominos.get(startingPieceIndex);
            Domino dominoToMergeWith = dominos.get(firstIndex);

            // check to see if you can merge starting piece with current piece
            if (!arePiecesMatching(dominoToCompare, dominoToMergeWith)) {
                continue;
            }

            // if this is reached it means we can do the merge
            if (isMatchingDominos(dominoToCompare.getFront(), dominoToMergeWith.getFront())) {
                currentSum += dominoToCompare.getFront().getValue();
                dominoToCompare = createMergedDomino(dominoToCompare.getBack(), dominoToMergeWith.getBack());
            } else if (isMatchingDominos(dominoToCompare.getFront(), dominoToMergeWith.getBack())) {
                currentSum += dominoToCompare.getFront().getValue();
                dominoToCompare = createMergedDomino(dominoToCompare.getBack(), dominoToMergeWith.getFront());
            } else if (isMatchingDominos(dominoToCompare.getBack(), dominoToMergeWith.getFront())) {
                currentSum += dominoToCompare.getBack().getValue();
                dominoToCompare = createMergedDomino(dominoToCompare.getFront(), dominoToMergeWith.getBack());
            } else if (isMatchingDominos(dominoToCompare.getBack(), dominoToMergeWith.getBack())) {
                currentSum += dominoToCompare.getBack().getValue();
                dominoToCompare = createMergedDomino(dominoToCompare.getFront(), dominoToMergeWith.getFront());
            }

            // dominoToCompare is merge of 2 pieces, iterate through all pieces and find other pieces to merge with
            for (int secondIndex = 0; secondIndex < dominosSize; secondIndex++) {
                // skip the starting pieces that were already merged
                if (secondIndex == startingPieceIndex || secondIndex == firstIndex) {
                    continue;
                }
                Domino currentDomino = dominos.get(secondIndex);
                if (isMatchingDominos(dominoToCompare.getFront(), currentDomino.getFront())) {
                    currentSum += dominoToCompare.getFront().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getBack(), currentDomino.getBack());
                } else if (isMatchingDominos(dominoToCompare.getFront(), currentDomino.getBack())) {
                    currentSum += dominoToCompare.getFront().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getBack(), currentDomino.getFront());
                } else if (isMatchingDominos(dominoToCompare.getBack(), currentDomino.getFront())) {
                    currentSum += dominoToCompare.getBack().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getFront(), currentDomino.getBack());
                } else if (isMatchingDominos(dominoToCompare.getBack(), currentDomino.getBack())) {
                    currentSum += dominoToCompare.getBack().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getFront(), currentDomino.getFront());
                } else {
                    // did not find any dominos to match then start again with next piece pair
                    if (highestSum < currentSum) {
                        highestSum = currentSum;
                        break;
                    }
                }

                // in case the last piece is merged we need to do another compare and set the new highest value
                if (highestSum < currentSum) {
                    highestSum = currentSum;
                }
            }
        }

        return highestSum;
    }

    private boolean arePiecesMatching(Domino dominoToCompare, Domino currentDomino) {

        if (isMatchingDominos(dominoToCompare.getFront(), currentDomino.getFront()) ||
                (isMatchingDominos(dominoToCompare.getFront(), currentDomino.getBack())) ||
                (isMatchingDominos(dominoToCompare.getBack(), currentDomino.getFront())) ||
                (isMatchingDominos(dominoToCompare.getBack(), currentDomino.getBack()))) {
            return true;
        }

        return false;
    }


    private int calculateValue(Domino startingDomino, List<Domino> dominos, int usedDominoIndex) {

        CalculationContext context = new CalculationContext();

        if (isNull(startingDomino)) {
            return context.getCurrentSum();
        }

        dominos.remove(usedDominoIndex);

        int currentHighestValue = 0;

        for (int index = 0; index < dominos.size(); index++) {
            Domino currentDomino = dominos.get(index);
            if (isMatchingDominos(startingDomino.getFront(), currentDomino.getFront())) {
                if (currentHighestValue < startingDomino.getFront().getValue()) {
                    currentHighestValue = startingDomino.getFront().getValue();
                    context = buildContext(startingDomino.getFront().getValue(), index,
                            createMergedDomino(startingDomino.getBack(), currentDomino.getBack()));
                }

            } else if (isMatchingDominos(startingDomino.getFront(), currentDomino.getBack())) {
                if (currentHighestValue < startingDomino.getFront().getValue()) {
                    currentHighestValue = startingDomino.getFront().getValue();
                    context = buildContext(startingDomino.getFront().getValue(), index,
                            createMergedDomino(startingDomino.getBack(), currentDomino.getFront()));
                }

            } else if (isMatchingDominos(startingDomino.getBack(), currentDomino.getFront())) {
                if (currentHighestValue < startingDomino.getBack().getValue()) {
                    currentHighestValue = startingDomino.getBack().getValue();
                    context = buildContext(startingDomino.getBack().getValue(), index,
                            createMergedDomino(startingDomino.getFront(), currentDomino.getBack()));
                }

            } else if (isMatchingDominos(startingDomino.getBack(), currentDomino.getBack())) {
                if (currentHighestValue < startingDomino.getBack().getValue()) {
                    currentHighestValue = startingDomino.getBack().getValue();
                    context = buildContext(startingDomino.getBack().getValue(), index,
                            createMergedDomino(startingDomino.getFront(), currentDomino.getFront()));
                }
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
