package com.app.domino.model;

/**
 * Model representing the Domino piece
 */
public class Domino {

    private Face front;
    private Face back;

    public Face getFront() {
        return front;
    }

    public void setFront(Face front) {
        this.front = front;
    }

    public Face getBack() {
        return back;
    }

    public void setBack(Face back) {
        this.back = back;
    }
}
