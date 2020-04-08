package com.example.minesweeper.logic;

public enum TileType {
    EMPTY,
    MINE,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    @Override
    public String toString() {
        switch (this){
            case EMPTY: return "";
            case MINE: return "M";
            case ONE: return "1";
            case TWO: return "2";
            case THREE: return "3";
            case FOUR: return "4";
            case FIVE: return "5";
            case SIX: return "6";
            case SEVEN: return "7";
            case EIGHT: return "8";
            default: return  "";
        }
    }
}
