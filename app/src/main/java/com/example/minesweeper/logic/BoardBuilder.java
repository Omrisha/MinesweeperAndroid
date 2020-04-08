package com.example.minesweeper.logic;

import java.util.Random;

public class BoardBuilder {
    private final static Random random = new Random();

    public static Tile[] buildBoard(final int length, int mines) {
        mines = Math.max(0, Math.min(mines, length));

        Tile[] board = new Tile[length];
        populateBoardWithCells(board);
        populateBoardWithMines(board, mines);
        return board;
    }

    private static void populateBoardWithCells(final Tile[] board) {
        for (int index = 0; index < board.length; index++) {
            board[index] = new Tile();
        }
    }

    private static void populateBoardWithMines(final Tile[] board, final int mines) {
        int numberOfMinesSet = 0;
        for (int index = 0; index < board.length; index++) {
            if (numberOfMinesSet < mines) {
                board[index].setmType(TileType.MINE);
                numberOfMinesSet++;
            }
        }
        shuffleBoard(board);
    }

    private static void shuffleBoard(final Tile[] board) {
        // Fisher–Yates algorithm
        for (int index = board.length - 1; index > 0; index--) {
            int indexRandom = random.nextInt(index + 1);

            Tile temp = board[index];
            board[index] = board[indexRandom];
            board[indexRandom] = temp;
        }
    }
}
