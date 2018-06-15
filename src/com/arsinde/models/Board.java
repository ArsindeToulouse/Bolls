package com.arsinde.models;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Cell[] cells;
    private List<Chain> chains = new ArrayList<>();
    private int colors;
    private int chainsLength;
    private int width;
    private int height;

    public Board(int colors, int chainsLength, int width, int height) {
        this.colors = colors;
        this.chainsLength = chainsLength;
        this.width = width;
        this.height = height;
        this.cells = new Cell[width * height];
    }

    public void addCell(int id, final Cell cell) {
        cells[id - 1] =  cell;
    }

    public Cell getCell(int id) {
        return cells[id - 1];
    }

    public void addChain(final Chain chain) {
        chains.add(chain);
    }

    public Chain getChain(int id) {
        return chains.get(id);
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setNeighbours(@Nullable final Cell cell) {

        if (cell != null) {
            int cellId = getCellId(cell);
            addCell(cellId - width, Cell.NeighboursKey.UP, cell);
            addCell(cellId - width + 1, Cell.NeighboursKey.URIGHT, cell);
            addCell(cellId + 1, Cell.NeighboursKey.RIGHT, cell);
            addCell(cellId + width + 1, Cell.NeighboursKey.DRIGHT, cell);
            addCell(cellId + width, Cell.NeighboursKey.DOWN, cell);
            addCell(cellId + width - 1, Cell.NeighboursKey.DLEFT, cell);
            addCell(cellId - 1, Cell.NeighboursKey.LEFT, cell);
            addCell(cellId - width - 1, Cell.NeighboursKey.ULEFT, cell);
        }
    }

    private int getCellId(final Cell cell) {
        return ((cell.getY() - 1) * width + cell.getX()) - 1;
    }

    private void addCell(final int id, Cell.NeighboursKey key, final Cell cell) {
        if (id > 0 && id < width * height) {
            Cell neighbour = cells[id];
            if (neighbour != null) {
                cell.addNeighbour(key, neighbour);
            }
        }
    }

    public void toConsole() {
        int count = width * height;
        for (int i=1; i <= height; i++) {
            for (int j=1; j <= width; j++) {
                int cellId = (i-1)*width + j;
                Cell cell = cells[cellId-1];
                if (cell != null) System.out.print("|" + cell.getColor().charAt(0));
                else System.out.print("|_");
            }
            System.out.print("|\n");
        }
    }
}
