package com.app.domino.context;

import com.app.domino.model.Domino;

import java.util.List;

/**
 * Context holding dominos and starting piece index
 */
public class DominoChainContext {

    private List<Domino> dominos;
    private int startingPieceIndex;

    public List<Domino> getDominos() {
        return dominos;
    }

    public void setDominos(List<Domino> dominos) {
        this.dominos = dominos;
    }

    public int getStartingPieceIndex() {
        return startingPieceIndex;
    }

    public void setStartingPieceIndex(int startingPieceIndex) {
        this.startingPieceIndex = startingPieceIndex;
    }
}
