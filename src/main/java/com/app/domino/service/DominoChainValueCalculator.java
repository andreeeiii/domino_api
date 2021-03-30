package com.app.domino.service;

import com.app.domino.context.CalculationContext;
import com.app.domino.model.Domino;
import com.app.domino.model.Face;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Integer> firstMergedPieceIndexes = new HashSet<>();

        // iterate through all the pieces
        for (int index = 0; index < dominosSize; index++) {

            int currentSum = 0;

            Domino dominoToCompare = dominos.get(startingPieceIndex);

            Set<Integer> usedPiecesIndex = new HashSet<>();

            boolean wasFirstPieceFound = false;

            for (int secondIndex = 0; secondIndex < dominosSize; secondIndex++) {

                // we cannot merge the current domino with the starting piece or the piece that is already in the chain
                if (secondIndex == startingPieceIndex || usedPiecesIndex.contains(secondIndex)) {
                    continue;
                }

                // we start merging the starting piece with the first piece that was not chosen before
                if (!wasFirstPieceFound && firstMergedPieceIndexes.contains(secondIndex)) {
                    continue;
                }

                Domino dominoToMergeWith = dominos.get(secondIndex);

                // to not repeat the same starting point which is the starting piece and the first piece found
                if (arePiecesMatching(dominoToCompare, dominoToMergeWith) && !wasFirstPieceFound) {
                    wasFirstPieceFound = true;
                    firstMergedPieceIndexes.add(secondIndex);
                }

                // calculate new chain and current sum and reset index to retry finding new dominos with the new chain
                if (isMatchingDominos(dominoToCompare.getFront(), dominoToMergeWith.getFront())) {
                    currentSum += dominoToCompare.getFront().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getBack(), dominoToMergeWith.getBack());
                    usedPiecesIndex.add(secondIndex);
                    secondIndex = 0;
                } else if (isMatchingDominos(dominoToCompare.getFront(), dominoToMergeWith.getBack())) {
                    currentSum += dominoToCompare.getFront().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getBack(), dominoToMergeWith.getFront());
                    usedPiecesIndex.add(secondIndex);
                    secondIndex = 0;
                } else if (isMatchingDominos(dominoToCompare.getBack(), dominoToMergeWith.getFront())) {
                    currentSum += dominoToCompare.getBack().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getFront(), dominoToMergeWith.getBack());
                    usedPiecesIndex.add(secondIndex);
                    secondIndex = 0;
                } else if (isMatchingDominos(dominoToCompare.getBack(), dominoToMergeWith.getBack())) {
                    currentSum += dominoToCompare.getBack().getValue();
                    dominoToCompare = createMergedDomino(dominoToCompare.getFront(), dominoToMergeWith.getFront());
                    usedPiecesIndex.add(secondIndex);
                    secondIndex = 0;
                }

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
