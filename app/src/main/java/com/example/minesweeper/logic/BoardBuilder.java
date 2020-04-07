package com.example.minesweeper.logic;

import java.util.Random;

public class BoardBuilder {
    private final static Random random = new Random();

    public static Tile[][] buildBoard(final int rows, final int columns, int mines) {
        mines = Math.max(0, Math.min(mines, rows * columns));

        Tile[][] board = new Tile[rows][columns];
        populateBoardWithCells(board);
        populateBoardWithMines(board, mines);
        return board;
    }

    private static void populateBoardWithCells(final Tile[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = new Tile();
            }
        }
    }

    private static void populateBoardWithMines(final Tile[][] board, final int mines) {
        int numberOfMinesSet = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (numberOfMinesSet < mines) {
                    board[row][col].setmType(TileType.MINE);
                    numberOfMinesSet++;
                }
            }
        }
        shuffleBoard(board);
    }

    private static void shuffleBoard(final Tile[][] board) {
        // Fisher–Yates algorithm
        for (int row = board.length - 1; row > 0; row--) {
            for (int col = board[row].length - 1; col > 0; col--) {
                int rowRandom = random.nextInt(row + 1);
                int colRandom = random.nextInt(col + 1);

                Tile temp = board[row][col];
                board[row][col] = board[rowRandom][colRandom];
                board[rowRandom][colRandom] = temp;

            }
        }
    }
}
