package com.wordris.wordrisproject;

public class GameManager {
    private int currentRoundScore;
    private Board currentBoard;
    private boolean isRunning;
    private boolean isPaused;

    public GameManager() {
        this.currentRoundScore = 0;
        this.isRunning = false;
        this.isPaused = false;
        this.currentBoard = new Board();
    }

    public boolean startGame() {
        if (isRunning) return false;

        try {
            currentRoundScore = 0;
            currentBoard.resetBoard();
            isRunning = true;
            isPaused = false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updatePerFrame() {
        if (!isRunning || isPaused) return;

        // TODO: trigger word check via WordCalculator here
        // TODO: call currentBoard.removeWord() on valid results
    }

    public boolean pauseGame() {
        if (!isRunning || isPaused) return false;
        isPaused = true;
        return true;
    }

    public boolean resumeGame() {
        if (!isRunning || !isPaused) return false;
        isPaused = false;
        return true;
    }

    public boolean endGame() {
        if (!isRunning) return false;
        isRunning = false;
        isPaused = false;
        return true;
    }

    public void updateScore(int points) {
        if (isRunning && !isPaused) {
            currentRoundScore += points;
        }
    }

    public int getCurrentRoundScore() {
        return currentRoundScore;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }
}