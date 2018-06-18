package com.arsinde;

import com.arsinde.models.Board;
import com.arsinde.models.Cell;

public class BollsApp {

    public static void main(String[] args) {

        Board board = new Board(3,3, 7, 9);
        board.addCell(3, new Cell(3, 1, "blue"));
        board.addCell(10, new Cell(3, 2, "blue"));
        board.addCell(17, new Cell(3, 3, "blue"));
        board.addCell(6, new Cell(6, 1, "blue"));
        board.addCell(12, new Cell(5, 2, "blue"));
        board.addCell(18, new Cell(4, 3, "blue"));
        board.addCell(54, new Cell(5, 8, "blue"));
        board.addCell(11, new Cell(4, 2, "yellow"));
        board.addCell(19, new Cell(5, 3, "yellow"));
        board.addCell(27, new Cell(6, 4, "yellow"));
        board.addCell(35, new Cell(7, 5, "yellow"));
        board.addCell(60, new Cell(4, 9, "yellow"));
        board.addCell(30, new Cell(2, 5, "green"));
        board.addCell(32, new Cell(4, 5, "green"));
        board.addCell(38, new Cell(3, 6, "green"));
        board.addCell(39, new Cell(4, 6, "green"));
        board.addCell(40, new Cell(5, 6, "green"));
        board.addCell(41, new Cell(6, 6, "green"));
        board.addCell(44, new Cell(2, 7, "green"));
        board.addCell(47, new Cell(5, 7, "green"));
        board.addCell(50, new Cell(1, 8, "green"));

        for (Cell item : board.getCells()) {
            board.setNeighbours(item);
        }

        board.toConsole();
        System.out.print("\n\n");

        board.buildChains();
        board.deleteChains();
    }
}
