package com.arsinde.models;

import com.sun.istack.internal.Nullable;

import java.util.*;

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

    public void buildChains() {

        for (Cell item : cells) {
            if (item != null) {
                checkNeighboursInChains(item);
            }
        }

    }

    public void deleteChains() {
        for (Chain chain : chains) {
            if (chain.getCellsChain().size() >= chainsLength) {
                for (Cell cell : chain.getCellsChain()) deleteCellFromArray(cell);
            }
        }
        toConsole();
    }

    private void deleteCellFromArray(final Cell cell) {
        for (int i = 0; i < height * width; i++) {
            int cellId = (cell.getY() - 1)*width + cell.getX();
            cells[cellId - 1] = null;
        }
    }

    private void checkNeighboursInChains(Cell item) {
        if (chains.size() != 0) {
            ArrayList<Chain> chainsForContinuation = new ArrayList<>();
            HashMap<Cell.NeighboursKey, Cell> newChains = new HashMap<>();
            for (Chain chain : chains) {
                ArrayDeque<Cell> test = chain.getCellsChain();
                boolean isExist = test.getFirst().getNeighbours().containsValue(item) || test.getLast().getNeighbours().containsValue(item);
                if (isExist) {
                    chainsForContinuation.add(chain);
                } else {
                    if (!item.getNeighbours().containsKey(Cell.NeighboursKey.LEFT)) newChains.put(Cell.NeighboursKey.LEFT, item);
                    if (!item.getNeighbours().containsKey(Cell.NeighboursKey.ULEFT)) newChains.put(Cell.NeighboursKey.ULEFT, item);
                    if (!item.getNeighbours().containsKey(Cell.NeighboursKey.UP)) newChains.put(Cell.NeighboursKey.UP, item);
                    if (!item.getNeighbours().containsKey(Cell.NeighboursKey.URIGHT)) newChains.put(Cell.NeighboursKey.URIGHT, item);
                }
            }
            if (chainsForContinuation.size() != 0) {
                for (Chain newItem : chainsForContinuation) {
                    checkCell(item, newItem);
                }
            }
            if (newChains.size() != 0) {
                for (Map.Entry cell : newChains.entrySet()) {
                    createChain((Cell) cell.getValue());
                }
            }
        } else {
            createChain(item);
        }
    }

    private void createChain(final Cell item) {
        Chain lr = new Chain(Chain.ChainDirection.LEFT_RIGHT);
        Cell lrCell = item.cloneCell();
            lrCell.deleteNeighbour(Cell.NeighboursKey.UP);
        lrCell.deleteNeighbour(Cell.NeighboursKey.DOWN);
            lrCell.deleteNeighbour(Cell.NeighboursKey.DLEFT);
            lrCell.deleteNeighbour(Cell.NeighboursKey.ULEFT);
            lrCell.deleteNeighbour(Cell.NeighboursKey.URIGHT);
            lrCell.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
            if (lrCell.getNeighbours().size() != 0) {
                lr.addCell(lrCell);
                chains.add(lr);
            }
        Chain uldr = new Chain(Chain.ChainDirection.ULEFT_DRIGHT);
        Cell uldrCell = item.cloneCell();
            uldrCell.deleteNeighbour(Cell.NeighboursKey.UP);
            uldrCell.deleteNeighbour(Cell.NeighboursKey.DLEFT);
            uldrCell.deleteNeighbour(Cell.NeighboursKey.URIGHT);
            uldrCell.deleteNeighbour(Cell.NeighboursKey.DOWN);
            uldrCell.deleteNeighbour(Cell.NeighboursKey.LEFT);
            uldrCell.deleteNeighbour(Cell.NeighboursKey.RIGHT);
            if (uldrCell.getNeighbours().size() != 0) {
                uldr.addCell(uldrCell);
                chains.add(uldr);
            }
        Chain ud = new Chain(Chain.ChainDirection.UP_DOWN);
        Cell udCell = item.cloneCell();
            udCell.deleteNeighbour(Cell.NeighboursKey.DLEFT);
            udCell.deleteNeighbour(Cell.NeighboursKey.URIGHT);
            udCell.deleteNeighbour(Cell.NeighboursKey.LEFT);
            udCell.deleteNeighbour(Cell.NeighboursKey.RIGHT);
            udCell.deleteNeighbour(Cell.NeighboursKey.ULEFT);
            udCell.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
            if (udCell.getNeighbours().size() != 0) {
                ud.addCell(udCell);
                chains.add(ud);
            }
        Chain urdl = new Chain(Chain.ChainDirection.URIGHT_DLEFT);
        Cell urdlCell = item.cloneCell();
            urdlCell.deleteNeighbour(Cell.NeighboursKey.UP);
            urdlCell.deleteNeighbour(Cell.NeighboursKey.DOWN);
            urdlCell.deleteNeighbour(Cell.NeighboursKey.LEFT);
            urdlCell.deleteNeighbour(Cell.NeighboursKey.RIGHT);
            urdlCell.deleteNeighbour(Cell.NeighboursKey.ULEFT);
            urdlCell.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
            if (urdlCell.getNeighbours().size() != 0) {
                urdl.addCell(urdlCell);
                chains.add(urdl);
            }
    }

    private boolean checkCell(final Cell item, final Chain chain) {
        Cell cellFirst = chain.getFirstCell();
        Cell cellLast = chain.getLastCell();
        boolean addedIntoChain = false;

        if (chain.getDirection() == Chain.ChainDirection.LEFT_RIGHT) {
            if (item.getNeighbour(Cell.NeighboursKey.RIGHT) != null && item.getNeighbour(Cell.NeighboursKey.RIGHT).equals(cellFirst)) {
                Cell lrCellRight = item.cloneCell();
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.UP);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    lrCellRight.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    addedIntoChain = chain.addFirstIntoChain(lrCellRight);
            }
            if (item.getNeighbour(Cell.NeighboursKey.LEFT) != null && item.getNeighbour(Cell.NeighboursKey.LEFT).equals(cellLast)) {
                Cell lrCellDown = item.cloneCell();
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.UP);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    lrCellDown.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    addedIntoChain = chain.addLastIntoChain(lrCellDown);
            }
        }

        if (chain.getDirection() == Chain.ChainDirection.ULEFT_DRIGHT) {
            if (item.getNeighbour(Cell.NeighboursKey.DRIGHT) != null && item.getNeighbour(Cell.NeighboursKey.DRIGHT).equals(cellFirst)) {
                Cell uldrCellDright = item.cloneCell();
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.UP);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    uldrCellDright.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    addedIntoChain = chain.addFirstIntoChain(uldrCellDright);
            }
            if (item.getNeighbour(Cell.NeighboursKey.ULEFT) != null && item.getNeighbour(Cell.NeighboursKey.ULEFT).equals(cellLast)) {
                Cell uldrCellUleft = item.cloneCell();
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.UP);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    uldrCellUleft.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    addedIntoChain = chain.addLastIntoChain(uldrCellUleft);
            }
        }

        if (chain.getDirection() == Chain.ChainDirection.UP_DOWN) {
            if (item.getNeighbour(Cell.NeighboursKey.DOWN) != null && item.getNeighbour(Cell.NeighboursKey.DOWN).equals(cellFirst)) {
                Cell udCellDown = item.cloneCell();
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    udCellDown.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    addedIntoChain = chain.addFirstIntoChain(udCellDown);
            }
            if (item.getNeighbour(Cell.NeighboursKey.UP) != null && item.getNeighbour(Cell.NeighboursKey.UP).equals(cellLast)) {
                Cell udCellUp = item.cloneCell();
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    udCellUp.deleteNeighbour(Cell.NeighboursKey.UP);
                    addedIntoChain = chain.addLastIntoChain(udCellUp);
            }
        }

        if (chain.getDirection() == Chain.ChainDirection.URIGHT_DLEFT) {
            if (item.getNeighbour(Cell.NeighboursKey.DLEFT) != null && item.getNeighbour(Cell.NeighboursKey.DLEFT).equals(cellFirst)) {
                Cell urdlCellDleft = item.cloneCell();
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.UP);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    urdlCellDleft.deleteNeighbour(Cell.NeighboursKey.DLEFT);
                    addedIntoChain = chain.addFirstIntoChain(urdlCellDleft);
            }
            if (item.getNeighbour(Cell.NeighboursKey.URIGHT) != null && item.getNeighbour(Cell.NeighboursKey.URIGHT).equals(cellLast)) {
                Cell urdlCellUright = item.cloneCell();
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.UP);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.DOWN);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.LEFT);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.RIGHT);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.ULEFT);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.DRIGHT);
                    urdlCellUright.deleteNeighbour(Cell.NeighboursKey.URIGHT);
                    addedIntoChain = chain.addLastIntoChain(urdlCellUright);
            }
        }
        return addedIntoChain;
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
