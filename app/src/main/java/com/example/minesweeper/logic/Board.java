package com.example.minesweeper.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Board {
    private Tile[][] mTiles;
    private int mMines = 0;

    private final HashMap<Tile,Integer> cacheRow = new HashMap<>();
    private final HashMap<Tile,Integer> cacheColumn = new HashMap<>();

    public Board(int colNumber, int rowNumber, Level difficulty) {
        this.mTiles = new Tile[rowNumber][colNumber];
        this.mMines = getMines(rowNumber * colNumber, difficulty);
        this.mTiles = BoardBuilder.buildBoard(rowNumber, colNumber, this.mMines);
        buildCache(this.mTiles);
    }

    public Tile chooseTile(int x, int y) {
        if (!inRange(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        return this.mTiles[x][y];
    }

    public int getRows() {
        return this.mTiles.length;
    }

    public int getColumns() {
        return this.mTiles[0].length;
    }

    public int getmMines() {
        return mMines;
    }

    public void revealBoard() {

    }

    public List<Tile> getNeighbourCells(final Tile cell) {
        int row = cacheRow.get(cell);
        int col = cacheColumn.get(cell);
        return getNeighbour(row, col);
    }

    public int getNeighbourMineCount(final int row, final int col) {
        return getNeighbourMineCount(chooseTile(row, col));
    }

    public int getNeighbourMineCount(Tile cell) {
        int amountOfNeighbourMines = 0;
        List<Tile> neighbours = getNeighbourCells(cell);
        for (Tile neighbour : neighbours) {
            if (neighbour.getmType().equals(TileType.MINE)) {
                amountOfNeighbourMines++;
            }
        }
        return amountOfNeighbourMines;
    }

    public void moveMineToRandomCell(final int row, final int col) {
        // ToDo performance optimizations? Could be inefficient if there are many mines
        Tile cell = chooseTile(row, col);
        if (!cell.getmType().equals(TileType.MINE)) {
            return;
        }
        int rowNew = row;
        int colNew = col;
        Random ran = new Random();
        while (rowNew == row || colNew == col || chooseTile(rowNew, colNew).getmType().equals(TileType.MINE)) {

            rowNew = ran.nextInt(getRows());
            colNew = ran.nextInt(getColumns());
        }
        cell.setmType(TileType.EMPTY);
        chooseTile(rowNew, colNew).setmType(TileType.MINE);
    }

    public void flagTile(int x, int y) {
        this.mTiles[x][y].setmIsFlagged(!this.mTiles[x][y].getmIsFlagged());
    }

    public List<Tile> getNeighbour(int row, int col) {
        List<Tile> neighbours = new ArrayList<Tile>();

        if (inRange(row - 1, col - 1)) {
            neighbours.add(this.mTiles[row - 1][col - 1]);
        }
        if (inRange(row - 1, col)) {
            neighbours.add(this.mTiles[row - 1][col]);
        }
        if (inRange(row - 1, col + 1)) {
            neighbours.add(this.mTiles[row - 1][col + 1]);
        }
        if (inRange(row, col - 1)) {
            neighbours.add(this.mTiles[row][col - 1]);
        }
        if (inRange(row, col + 1)) {
            neighbours.add(this.mTiles[row][col + 1]);
        }
        if (inRange(row + 1, col - 1)) {
            neighbours.add(this.mTiles[row + 1][col - 1]);
        }
        if (inRange(row + 1, col)) {
            neighbours.add(this.mTiles[row + 1][col]);
        }
        if (inRange(row + 1, col + 1)) {
            neighbours.add(this.mTiles[row + 1][col + 1]);
        }
        return neighbours;
    }

    private void buildCache(final Tile[][] field) {
        cacheRow.clear();
        cacheColumn.clear();

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                cacheRow.put(field[row][col], row);
                cacheColumn.put(field[row][col], col);
            }
        }
    }

    private int getMines(final int cells, final Level difficulty) {
        return (int) (difficulty.getPercentage() * cells);
    }

    private boolean inRange(final int row, final int col) {
        if (row < 0 || row >= getRows() || col < 0 || col >= getColumns()) {
            return false;
        } else {
            return this.mTiles[row][col] != null;
        }
    }
}
