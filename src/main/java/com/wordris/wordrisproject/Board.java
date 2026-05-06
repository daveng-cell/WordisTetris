package com.wordris.wordrisproject;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayDeque;
import java.util.Queue;

public class Board {
    // Global variables: BASE_GRID represents the size of a 1x1 space in a grid
    static final int BASE_GRID = 25;
    static final int X_MAX = BASE_GRID * 12;
    static final int Y_MAX = BASE_GRID * 24;

    // letterBoard holds the data for what char is in which coordinate of the grid for the board
    // visualBoard is the visual of the board as a pane
    private final char[][] letterBoard = new char[Y_MAX / BASE_GRID][X_MAX / BASE_GRID];
    private final Rectangle[][] visualGrid = new Rectangle[Y_MAX / BASE_GRID][X_MAX / BASE_GRID];

    private final Pane visualBoard = new Pane();

    // These items are separate from the board, but interact with the board in some way
    private Polyomino current;
    private Polyomino reserved;
    private Queue<Polyomino> polyominoQueue;
    private WordCalculator wordCalculator;

    public Board() {
        polyominoQueue = new ArrayDeque<>();
        // wordCalculator = new WordCalculator();
        visualBoard.setPrefSize(X_MAX, Y_MAX);
        visualBoard.setStyle("-fx-background-color: black;");
    }

    // Clears the board and resets the letterBoard state 
    public boolean resetBoard() {
        try {
            visualBoard.getChildren().clear(); 

            for (int row = 0; row < Y_MAX / BASE_GRID; row++) {
                for (int col = 0; col < X_MAX / BASE_GRID; col++) {
                    letterBoard[row][col] = '\0';
                    visualGrid[row][col] = null; 
                }
            }
            current = null;
            reserved = null;
            polyominoQueue.clear();
            getNextPolyomino();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Allows user to remove a certain amount of blocks from one coordinate to another, 
    // then pushes any blocks above downward
    // returns boolean values to see if each function went through successfully or not
    public boolean removeWord(int xFrom, int yFrom, int xTo, int yTo) {
        int colFrom = xFrom/BASE_GRID; 
        int colTo = xTo/BASE_GRID; 
        int rowFrom = yFrom/BASE_GRID; 
        int rowTo = yTo/BASE_GRID;
        
        // check bounds 
        if (colFrom < 0 || colTo >= X_MAX / BASE_GRID) return false;
        if (rowFrom < 0 || rowTo >= Y_MAX / BASE_GRID) return false;
        if (rowFrom != rowTo) return false; // only horizontal removal

        // clear cells 
        for (int col = colFrom; col <= colTo; col++) {
            visualBoard.getChildren().remove(visualGrid[rowFrom][col]);
            visualGrid[rowFrom][col] = null; 
            letterBoard[rowFrom][col] = '\0'; 
        }

        // shift everything above rowFrom down by one row
        for (int row = rowFrom; row > 0; row--) {
            for (int col = colFrom; col <= colTo; col++) {
                letterBoard[row][col] = letterBoard[row - 1][col];
                visualGrid[row][col] = visualGrid[row - 1][col];
                if (visualGrid[row][col] != null) {
                    visualGrid[row][col].setY(row * BASE_GRID);
                }
            }
        }

        // clear the top row in that column range
        for (int col = colFrom; col <= colTo; col++) {
            letterBoard[0][col] = '\0';
            visualGrid[0][col] = null;
        }

        return true;
    }

    //  Allows user to move  controllable polyomino
    //  -1 = left, 1 = right, no moving up or down
    public void moveCurrentPolyomino(int direction) {
        int delta = BASE_GRID * direction; // negative = left 

        // check every block is within bounds/not trying to occupy a non-empty cell
        for (Rectangle block : current.getShapes()) {
            double newX = block.getX() + delta; 
            int col = (int) newX/BASE_GRID; 
            int row = (int) block.getY() / BASE_GRID; 

            if (newX < 0 || newX >= X_MAX) return; // out of bounds 
            if (letterBoard[row][col] != '\0') return; // cell occupied 
        }

        // apply delta to all blocks once it is safe to move
        for (Rectangle block : current.getShapes()) {
            block.setX(block.getX() + delta);
        }
    }

    // Swaps current controllable block with the reserved block if reserved not null
    // else, pulls from queue 
    public void switchReservedPolyomino() {
        if (reserved == null) {
            reserved = current; 
            getNextPolyomino();
        } else {
            Polyomino temp = current; 
            current = reserved; 
            reserved = temp; 
        }
    }

    // Places block, from any height down to the lowest possible position on grid
    public void placeCurrentPolyomino() {
        int minDrop = Integer.MAX_VALUE; 

        // calculate how far the block can drop 
        for (Rectangle block : current.getShapes()) {
            int col = (int) block.getX()/BASE_GRID; 
            int startRow = (int) block.getY()/BASE_GRID; 

            int drop = 0; 
            for (int row = startRow +1; row < Y_MAX/BASE_GRID; row++) {
                if (letterBoard[row][col] != '\0') break; 
                drop++; 
            }
            minDrop = Math.min(minDrop, drop); 
        }

        // move all blocks down by minDrop rows 
        for (Rectangle block : current.getShapes()) {
            block.setY(block.getY() + minDrop * BASE_GRID);
        }

        // lock into letterBoard 
        char[] letters = current.getLetters(); 
        Rectangle[] shapes = current.getShapes(); 
        for (int i = 0; i < current.getSize(); i++) { 
            int col = (int) shapes[i].getX()/BASE_GRID; 
            int row = (int) shapes[i].getY()/BASE_GRID;
            letterBoard[row][col] = letters[i];  
            visualGrid[row][col] = shapes[i];
        }

        getNextPolyomino(); 

    }

    // Gets next polyomino in queue 
    public void getNextPolyomino() {
        if (!polyominoQueue.isEmpty()) {
            current = polyominoQueue.poll();
        }
    }

    // Returns the visual board Pane 
    public Pane getVisualBoard() {
        return visualBoard;
    }
}
