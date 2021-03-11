package com.app.domino.model;

/**
 * Model holding the value of one side of a domino piece
 */
public class Face {

    private int value;
    private Face connectedTo;

    public Face() {

    }

    public Face(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Face getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(Face connectedTo) {
        this.connectedTo = connectedTo;
    }

    public boolean isUsed() {

        return connectedTo != null;
    }
}
