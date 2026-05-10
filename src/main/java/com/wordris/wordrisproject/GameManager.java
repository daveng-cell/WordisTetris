package com.wordris.wordrisproject;

import java.util.List;

public class GameManager {
    private int currentRoundScore;
    private Board currentBoard;
    private boolean isRunning;
    private boolean isPaused;
    private WordCalculator wordCalculator;

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
    }

    public void onPiecePlaced() {
        if (!isRunning || isPaused) return;

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