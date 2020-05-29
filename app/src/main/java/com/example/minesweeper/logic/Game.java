package com.example.minesweeper.logic;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Game implements Serializable {
    private Board mBoard;
    private TimeCounter mTimeCounter;
    private Timer mTimer;
    private Level mLevel;

    public Game(Level mLevel) {
        this.mLevel = mLevel;
        this.mTimeCounter = new TimeCounter();
        this.mTimer = new Timer();

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

    public Board getmBoard() {
        return mBoard;
    }

    public void startTimer(){
        this.mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                increaseTime();
            }
        }, 0);
    }

    public void stopTimer() {
        this.mTimer.cancel();
    }

    public void startGame() {

    }

    private void increaseTime() {
        if (this.mTimeCounter != null) {
            this.mTimeCounter.increaseTime();
        }
    }
}
