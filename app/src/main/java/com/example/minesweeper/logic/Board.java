package com.example.minesweeper.logic;

public class Board {
    private Tile[][] mTiles;

    public Board(int colNumber, int rowNumber) {
        this.mTiles = new Tile[rowNumber][colNumber];

        for (int i = 0; i < rowNumber; i ++) {
            for (int j = 0; j < colNumber; j++) {
                this.mTiles[i][j] = new Tile();
            }
        }
    }

    public Tile chooseTile(int x, int y) {
        return this.mTiles[x][y];
    }

    public void drawBoard() {

    }

    public Boolean isGameOver() {

    }

    public void calculateDistance() {

    }

    public void revealBoard() {

    }

    public void flagTile(int x, int y) {
        this.mTiles[x][y].setmIsFlagged(!this.mTiles[x][y].getmIsFlagged());
    }
}
