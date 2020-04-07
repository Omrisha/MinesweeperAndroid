package com.example.minesweeper.logic;

public enum Level {
    BEGINNER(0.1),
    INTERMIDIATE(0.2),
    EXPERT(0.3);

    private final double percentage;

    Level(final double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return this.percentage;
    }

}
