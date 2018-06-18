package com.arsinde.models;

import java.util.HashMap;

public class Cell implements Cloneable {

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
        if (this.equalsColor(neighbour.getColor())) neighbours.put(key, neighbour);
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

    public HashMap<NeighboursKey, Cell> getNeighbours() {
        return neighbours;
    }

    void deleteNeighbour(NeighboursKey key) {
        neighbours.remove(key);
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
    private void setNeighbours(HashMap<NeighboursKey, Cell> set) {
        neighbours.putAll(set);
    }

    protected Cell cloneCell() {
        Cell newCell = new Cell(this.x, this.y, this.color);
        newCell.setNeighbours(this.neighbours);
        return newCell;
    }
}
