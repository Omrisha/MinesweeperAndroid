package com.example.minesweeper.logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Tile[] mTiles;
    private int mMines = 0;
    private int cols;
    private int rows;

    public Board(int colNumber, int rowNumber, Level difficulty) {
        this.cols = colNumber;
        this.rows = rowNumber;
        this.mTiles = new Tile[rowNumber*colNumber];
        this.mMines = getMines(rowNumber * colNumber, difficulty);
        this.mTiles = BoardBuilder.buildBoard(rowNumber*colNumber, this.mMines);
    }

    public Tile chooseTile(int index) {
        if (!inRange(index))
            throw new IndexOutOfBoundsException();
        return this.mTiles[index];
    }

    public int getCount() {
        return this.mTiles.length;
    }

    public int getmMines() {
        return mMines;
    }

    public int getNeighbourMineCount(int index) {
        int amountOfNeighbourMines = 0;
        List<Tile> neighbours = getNeighbourCells(index);
        for (Tile neighbour : neighbours) {
            if (neighbour.getmType().equals(TileType.MINE)) {
                amountOfNeighbourMines++;
            }
        }
        return amountOfNeighbourMines;
    }

    public void flagTile(int index) {
        this.mTiles[index].setmIsFlagged(!this.mTiles[index].getmIsFlagged());
    }

    public List<Tile> getNeighbourCells(int index) {
        List<Tile> neighbours = new ArrayList<Tile>();

        // -----------------------------------------------------------
        //                 NW | N     | NE
        //                 W  | index | E
        //                 SW | S     | SE
        // -----------------------------------------------------------
        // North West
        if (inRange(index - this.cols - 1)) {
            neighbours.add(this.mTiles[index - this.cols - 1]);
        }
        // North
        if (inRange(index - this.cols)) {
            neighbours.add(this.mTiles[index - this.cols]);
        }
        // North East
        if (inRange(index - this.cols + 1)) {
            neighbours.add(this.mTiles[index - this.cols + 1]);
        }
        // West
        if (inRange(index - 1)) {
            neighbours.add(this.mTiles[index - 1]);
        }
        // East
        if (inRange(index + 1)) {
            neighbours.add(this.mTiles[index + 1]);
        }
        // South West
        if (inRange(index + this.cols - 1)) {
            neighbours.add(this.mTiles[index + this.cols - 1]);
        }
        // South
        if (inRange(index + this.cols)) {
            neighbours.add(this.mTiles[index + this.cols]);
        }
        // South East
        if (inRange(index + this.cols + 1)) {
            neighbours.add(this.mTiles[index + this.cols + 1]);
        }
        return neighbours;
    }

    private int getMines(final int cells, final Level difficulty) {
        return (int) (difficulty.getPercentage() * cells);
    }

    private boolean inRange(final int index) {
        if (index < 0 || index > getCount()) {
            return false;
        } else {
            return this.mTiles[index] != null;
        }
    }
}
