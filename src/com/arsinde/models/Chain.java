package com.arsinde.models;

import java.util.ArrayDeque;

public class Chain {

    private ArrayDeque<Cell> cellsChain = new ArrayDeque<>();
    private ChainDirection direction;

    public enum ChainDirection {
        LEFT_RIGHT, ULEFT_DRIGHT, UP_DOWN, URIGHT_DLEFT
    }

    public Chain(ChainDirection direction) {
        this.direction = direction;
    }

    void addCell(final Cell cell) {
        this.cellsChain.add(cell);
    }

    public ArrayDeque<Cell> getCellsChain() {
        return cellsChain;
    }

    public Cell getFirstCell() {
        return cellsChain.peekFirst();
    }

    public Cell getLastCell() {
        return cellsChain.peekLast();
    }

    public ChainDirection getDirection() {
        return direction;
    }

    public boolean addFirstIntoChain(final Cell cell) {
        return this.cellsChain.offerFirst(cell);
    }

    public boolean addLastIntoChain(final Cell cell) {
        return this.cellsChain.offerLast(cell);
    }
}
