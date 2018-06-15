package com.arsinde.models;

import java.util.HashMap;

public class Cell {

    private int x;
    private int y;
    private String color;
    private HashMap<NeighboursKey, Cell> neighbours = new HashMap<>();

    public enum NeighboursKey {
        UP, DOWN, LEFT, RIGHT, ULEFT, DRIGHT, URIGHT, DLEFT
    }

    public Cell(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    void addNeighbour(NeighboursKey key, final Cell neighbour) {
        neighbours.put(key, neighbour);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    String getColor() {
        return color;
    }

    Cell getNeighbour(NeighboursKey key) {
        return neighbours.get(key);
    }

    boolean equals(final Cell cell) {
        if (this.equalsColor(cell.getColor())) {
            return (this.x == cell.getX()) && (this.y == cell.getY());
        }
        return false;
    }

    private boolean equalsColor(final String color) {
        return this.color.equalsIgnoreCase(color);
    }
}
