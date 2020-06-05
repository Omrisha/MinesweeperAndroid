package com.example.minesweeper.logic;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {
    private Board mBoard;
    private Level mLevel;

    public Game(Level mLevel) {
        this.mLevel = mLevel;

        switch (this.mLevel) {
            case BEGINNER:
                this.mBoard = new Board(8, 8, this.mLevel);
                break;
            case INTERMEDIATE:
                this.mBoard = new Board(13, 15, this.mLevel);
                break;
            case EXPERT:
                this.mBoard = new Board(16, 30, this.mLevel);
                break;
        }
    }
//
    public Board getmBoard() {
        return mBoard;
    }

    public void flagATile(int position) { this.mBoard.flagTile(position); }

    public void handlePenalty(){
        List<Tile> revealedTiles = this.mBoard.getRevealedTileList();
        if (revealedTiles.size() > 0){
            Random rand = new Random();
            int randomIndex = rand.nextInt(revealedTiles.size()-1);
            Tile removed = revealedTiles.remove(randomIndex);
            if (!removed.getmType().equals(TileType.EMPTY))
                removed.setmType(TileType.EMPTY);
            int index = Board.find(this.mBoard.getmTiles(), removed);
            this.mBoard.flipTile(index);
            mBoard.setmMines(mBoard.getmMines()+1);
        }
    }
}
