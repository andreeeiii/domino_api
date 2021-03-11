package com.app.domino.context;

import com.app.domino.model.Domino;

/**
 * Model holding value used for calculating domino chain value
 */
public class CalculationContext {

    private int currentSum;
    private int usedDominoIndex;
    private Domino mergedDomino;

    public int getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(int currentSum) {
        this.currentSum = currentSum;
    }

    public int getUsedDominoIndex() {
        return usedDominoIndex;
    }

    public void setUsedDominoIndex(int usedDominoIndex) {
        this.usedDominoIndex = usedDominoIndex;
    }

    public Domino getMergedDomino() {
        return mergedDomino;
    }

    public void setMergedDomino(Domino mergedDomino) {
        this.mergedDomino = mergedDomino;
    }
}
