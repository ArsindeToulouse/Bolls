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

    public void addNeighbour(NeighboursKey key, final Cell neighbour) {
        neighbours.put(key, neighbour);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public Cell getNeighbour(NeighboursKey key) {
        return neighbours.get(key);
    }

    public boolean equalsColor(final String color) {
        return this.color.equalsIgnoreCase(color);
    }
}
