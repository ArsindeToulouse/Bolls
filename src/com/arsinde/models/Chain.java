package com.arsinde.models;

import java.util.ArrayDeque;

public class Chain {

    private String chainTitle;
    private ArrayDeque<Cell> cellsChain = new ArrayDeque<>();

    public Chain(String chainTitle) {
        this.chainTitle = chainTitle;
    }

    public String getChainTitle() {
        return chainTitle;
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
}
