package com.wordris.wordrisproject;

import java.util.List;

public class GameManager {
    private int currentRoundScore;
    private Board currentBoard;
    private boolean isRunning;
    private boolean isPaused;
    private WordCalculator wordCalculator;

    private long lastGravityTime = 0;
    private long gravityInterval = 1000_000_000L; // 1 second in nanoseconds

    public GameManager() {
        this.currentRoundScore = 0;
        this.isRunning = false;
        this.isPaused = false;
        this.currentBoard = new Board();
        this.wordCalculator = new WordCalculator();
    }

    public boolean startGame() {
        if (isRunning) return false;
        try {
            currentRoundScore = 0;
            lastGravityTime = 0;
            gravityInterval = 1000_000_000L;
            isRunning = true;
            isPaused = false;
            currentBoard.getNextPolyomino();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePerFrame(long now) {
        if (!isRunning || isPaused) return;

        if (currentBoard.isGameOver()) {
            endGame();
            return;
        }

        if (lastGravityTime == 0) {
            lastGravityTime = now;
        }

        if (now - lastGravityTime >= gravityInterval) {
            lastGravityTime = now;
            boolean moved = currentBoard.stepDown();
            if (!moved) {
                currentBoard.placeCurrentPolyomino();
                onPiecePlaced();
            }
            updateGravitySpeed();
        }
    }

    private void updateGravitySpeed() {
        // every 5 points speed up by 50ms, minimum 150ms
        long minInterval = 150_000_000L;
        long speedup = (currentRoundScore / 5) * 50_000_000L;
        gravityInterval = Math.max(minInterval, 1000_000_000L - speedup);
    }

    public void onPiecePlaced() {
        if (!isRunning || isPaused) return;

        if (currentBoard.isGameOver()) {
            endGame();
            return;
        }

        List<WordResult> foundWords = wordCalculator.checkForWords(currentBoard);
        for (WordResult result : foundWords) {
            updateScore(result.getScore());
            int fixedPixel = result.getFixedIndex() * Board.BASE_GRID;
            int startPixel = result.getStart() * Board.BASE_GRID;
            int endPixel = result.getEnd() * Board.BASE_GRID;
            if (result.isHorizontal()) {
                currentBoard.removeWord(startPixel, fixedPixel, endPixel, fixedPixel);
            }
        }
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

    public int getCurrentRoundScore() { return currentRoundScore; }
    public Board getCurrentBoard() { return currentBoard; }
    public boolean isRunning() { return isRunning; }
    public boolean isPaused() { return isPaused; }
}