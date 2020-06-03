package com.example.minesweeper.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board implements Serializable {
    private Tile[] mTiles;
    private int mMines = 0;
    private int cols;
    private int rows;
    private int mRevealedCells = 0;
    private Level mLevel;

    public Board(int colNumber, int rowNumber, Level difficulty) {
        this.cols = colNumber;
        this.rows = rowNumber;
        this.mLevel = difficulty;
        this.mTiles = new Tile[rowNumber*colNumber];
        this.mMines = getMines(rowNumber * colNumber, difficulty);
        this.mTiles = BoardBuilder.buildBoard(rowNumber*colNumber, this.mMines);
    }

    public Level getmLevel() {
        return mLevel;
    }

    public Tile chooseTile(int index) {
        if (!inRange(index))
            throw new IndexOutOfBoundsException();
        return this.mTiles[index];
    }

    public int getRevealedCells() { return this.mRevealedCells; }

    public void setmRevealedCells(int mRevealedCells) {
        this.mRevealedCells = mRevealedCells;
    }

    public int getmMines() {
        return mMines;
    }

    public void setmMines(int mMines) {
        this.mMines = mMines;
    }

    public Boolean playTile(int index) {
        Tile tile = chooseTile(index);
        tile.setmIsRevealed(true);

        if(!tile.getmType().equals(TileType.MINE)) {
            this.mRevealedCells++;
            int mineCount = this.getNeighbourMineCount(index);
            if(mineCount == 0) {
                tile.setmType(TileType.EMPTY);
                List<Tile> neighbors = getNeighbourCells(index);
                for(int i = 0; i < neighbors.size(); i++) {
                    int neighborIndex = find(this.mTiles, neighbors.get(i));
                    if(!neighbors.get(i).getmIsRevealed()) {
                        playTile(neighborIndex);
                    }
                }
            } else {
                setTileTypeAccordingToMine(index, mineCount);
            }
        }
        return tile.getmType().equals(TileType.MINE);
    }

    public void setTileTypeAccordingToMine(int index, int mineCount) {
        switch (mineCount){
            case 1: this.mTiles[index].setmType(TileType.ONE); break;
            case 2: this.mTiles[index].setmType(TileType.TWO); break;
            case 3: this.mTiles[index].setmType(TileType.THREE); break;
            case 4: this.mTiles[index].setmType(TileType.FOUR); break;
            case 5: this.mTiles[index].setmType(TileType.FIVE); break;
            case 6: this.mTiles[index].setmType(TileType.SIX); break;
            case 7: this.mTiles[index].setmType(TileType.SEVEN); break;
            case 8: this.mTiles[index].setmType(TileType.EIGHT); break;
            default: this.mTiles[index].setmType(TileType.EMPTY); break;
        }
    }

    public Boolean isGameOver() {
        if (this.mRevealedCells + this.mMines == this.mTiles.length) {
            return true;
        } else {
            return false;
        }
    }

    public void revealBoard() {
        for (int i = 0 ; i < getCount(); i++){
            if (!this.mTiles[i].getmIsRevealed() && this.mTiles[i].getmType().equals(TileType.MINE)) {
                this.mTiles[i].setmIsRevealed(true);
            }
        }
    }

    public int getCols() {
        return cols;
    }

    public int getCount() {
        return this.mTiles.length;
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

    public void flipTile(int index){
        if (this.mTiles[index].getmIsRevealed())
            this.mRevealedCells--;
        else
            this.mRevealedCells++;
        this.mTiles[index].setmIsRevealed(!this.mTiles[index].getmIsRevealed());
    }

    public List<Tile> getNeighbourCells(int index) {
        List<Tile> neighbours = new ArrayList<Tile>();

        // -----------------------------------------------------------
        //                 NW | N     | NE
        //                 W  | index | E
        //                 SW | S     | SE
        // -----------------------------------------------------------
        // North West
        if (inRange(index - this.cols - 1) && (index%this.cols != 0) && (index/this.cols != 0)) {
            neighbours.add(this.mTiles[index - this.cols - 1]);
        }
        // North
        if (inRange(index - this.cols) && (index/this.cols != 0)) {
            neighbours.add(this.mTiles[index - this.cols]);
        }
        // North East
        if (inRange(index - this.cols + 1) && (index/this.cols != 0) && (index%this.cols != this.cols-1)) {
            neighbours.add(this.mTiles[index - this.cols + 1]);
        }
        // West
        if (inRange(index - 1) && (index%this.cols != 0)) {
            neighbours.add(this.mTiles[index - 1]);
        }
        // East
        if (inRange(index + 1) && (index%this.cols < this.cols-1)) {
            neighbours.add(this.mTiles[index + 1]);
        }
        // South West
        if (inRange(index + this.cols - 1)  && (index%this.cols != 0) && (index/this.cols != this.rows-1)) {
            neighbours.add(this.mTiles[index + this.cols - 1]);
        }
        // South
        if (inRange(index + this.cols) && (index/this.cols < this.rows-1)) {
            neighbours.add(this.mTiles[index + this.cols]);
        }
        // South East
        if (inRange(index + this.cols + 1) && (index/this.cols != this.rows-1) && (index%this.cols != this.cols-1)) {
            neighbours.add(this.mTiles[index + this.cols + 1]);
        }
        return neighbours;
    }

    private int getMines(final int cells, final Level difficulty) {
        return (int) (difficulty.getPercentage() * cells);
    }

    public List<Tile> getRevealedTileList(){
        List<Tile> items = new ArrayList<>();
        for (Tile tile : mTiles){
            if ((!tile.getmType().equals(TileType.MINE) || !tile.getmType().equals(TileType.FLAG)) && tile.getmIsRevealed().equals(true))
                items.add(tile);
        }
        return items;
    }

    private boolean inRange(final int index) {
        if (index < 0 || index >= getCount()) {
            return false;
        } else {
            return this.mTiles[index] != null;
        }
    }

    public Tile[] getmTiles() {
        return mTiles;
    }

    // Generic function to find the index of an element in an object array in Java
    public static<T> int find(T[] a, T target)
    {
        for (int i = 0; i < a.length; i++)
            if (target.equals(a[i]))
                return i;

        return -1;
    }
}
