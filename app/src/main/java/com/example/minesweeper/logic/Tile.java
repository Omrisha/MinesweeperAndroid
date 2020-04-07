package com.example.minesweeper.logic;

public class Tile {
    private TileType mType;
    private Boolean mIsRevealed;
    private Boolean mIsFlagged;

    public Tile() {
        this.mType = TileType.EMPTY;
        this.mIsFlagged = false;
        this.mIsRevealed = true;
    }

    public Tile(TileType mType, Boolean mIsRevealed, Boolean mIsFlagged) {
        this.mType = mType;
        this.mIsRevealed = mIsRevealed;
        this.mIsFlagged = mIsFlagged;
    }

    public TileType getmType() {
        return mType;
    }

    public void setmType(TileType mType) {
        this.mType = mType;
    }

    public Boolean getmIsRevealed() {
        return mIsRevealed;
    }

    public void setmIsRevealed(Boolean mIsRevealed) {
        this.mIsRevealed = mIsRevealed;
    }

    public Boolean getmIsFlagged() {
        return mIsFlagged;
    }

    public void setmIsFlagged(Boolean mIsFlagged) {
        this.mIsFlagged = mIsFlagged;
    }
}
