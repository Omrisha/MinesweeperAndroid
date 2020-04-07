package com.example.minesweeper.logic;

public class TimeCounter {
    private int mSeconds;
    private int mMinutes;
    private int mHours;

    public TimeCounter() {
        this.mHours = 0;
        this.mMinutes = 0;
        this.mSeconds = 0;
    }

    public void increaseTime() {
        if (this.mSeconds >= 0 && this.mSeconds < 60) {
            this.mSeconds++;
        } else {
            this.mSeconds = 0;
            if (this.mMinutes >= 0 && this.mMinutes < 60) {
                this.mMinutes++;
            } else {
                this.mMinutes = 0;
                if (this.mHours >= 0 && this.mHours < 60) {
                    this.mHours++;
                } else {
                    this.mHours = 0;
                }
            }
        }
    }
}
