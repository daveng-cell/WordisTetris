package com.wordris.wordrisproject;

import java.util.Queue;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Board {
    // Global variables: BASE_GRID represents the size of a 1x1 space in a grid
    static final int BASE_GRID = 25;
    static final int X_MAX = BASE_GRID * 12;
    static final int Y_MAX = BASE_GRID * 24;

    // letterBoard holds the data for what char is in which coordinate of the grid for the board
    // visualBoard is the visual of the board as a pane
    private final char[][] letterBoard = new char[Y_MAX / BASE_GRID][X_MAX / BASE_GRID];
    private final Group[][] visualGrid = new Group[Y_MAX / BASE_GRID][X_MAX / BASE_GRID];

    private final Pane visualBoard = new Pane();

    // These items are separate from the board, but interact with the board in some way
    private Polyomino current;
    private Polyomino reserved;
    private Queue<Polyomino> polyominoQueue;
    private PolyominoGenerator polyominoGenerator;
    private WordCalculator wordCalculator;

    public Board() {
        // Creates generator, then creates a queue of a particular size, which right now is just a sample number and should change
        polyominoGenerator = new PolyominoGenerator(PolyominoState.PREFIX);
        polyominoQueue = polyominoGenerator.generatePolyominos(40);

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
        int colFrom = xFrom / BASE_GRID;
        int colTo = xTo / BASE_GRID;
        int rowFrom = yFrom / BASE_GRID;
        int rowTo = yTo / BASE_GRID;

        if (colFrom < 0 || colTo >= X_MAX / BASE_GRID) return false;
        if (rowFrom < 0 || rowTo >= Y_MAX / BASE_GRID) return false;
        if (rowFrom != rowTo) return false;

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
                    // shift the rectangle inside the group
                    Rectangle rect = (Rectangle) visualGrid[row][col].getChildren().get(0);
                    rect.setY(row * BASE_GRID);
                }
            }
        }

        // clear top row in that column range
        for (int col = colFrom; col <= colTo; col++) {
            letterBoard[0][col] = '\0';
            visualGrid[0][col] = null;
        }

        return true;
    }

    //  Allows user to move  controllable polyomino
    //  -1 = left, 1 = right, no moving up or down
public void moveCurrentPolyomino(int direction) {
    int delta = BASE_GRID * direction;
    for (Rectangle block : current.getShapes()) {
        double newX = block.getX() + delta;

        if (newX < 0 || newX >= X_MAX) {
            System.out.println("Blocked by bounds");
            return;
        }

        int col = (int) newX / BASE_GRID;
        int row = (int) block.getY() / BASE_GRID;

        if (letterBoard[row][col] != '\0') {
            System.out.println("Blocked by cell");
            return;
        }
    }
    for (Rectangle block : current.getShapes()) {
        block.setX(block.getX() + delta);
    }
}

    // Swaps current controllable block with the reserved block if reserved not null
    // else, pulls from queue 
    public void switchReservedPolyomino() {
    if (reserved == null) {
        // remove current from visual board
        for (int i = 0; i < current.getSize(); i++) {
            visualBoard.getChildren().remove(current.getCells()[i]);
        }
        reserved = current;
        getNextPolyomino();
    } else {
        // remove current from visual board
        for (int i = 0; i < current.getSize(); i++) {
            visualBoard.getChildren().remove(current.getCells()[i]);
        }
        Polyomino temp = current;
        current = reserved;
        reserved = temp;

        // reset reserved piece position to top and re-add to visual board
        for (int i = 0; i < current.getSize(); i++) {
            Rectangle block = current.getShapes()[i];
            block.setX(i * BASE_GRID);
            block.setY(0);
            visualBoard.getChildren().add(current.getCells()[i]);
        }
    }
}

    // Places block, from any height down to the lowest possible position on grid
        public void placeCurrentPolyomino() {
        int minDrop = Integer.MAX_VALUE;

        for (Rectangle block : current.getShapes()) {
            int col = (int) block.getX() / BASE_GRID;
            int startRow = (int) block.getY() / BASE_GRID;

            int drop = 0;
            for (int row = startRow + 1; row < Y_MAX / BASE_GRID; row++) {
                if (letterBoard[row][col] != '\0') break;
                drop++;
            }
            minDrop = Math.min(minDrop, drop);
        }

        for (Rectangle block : current.getShapes()) {
            block.setY(block.getY() + minDrop * BASE_GRID);
        }

        char[] letters = current.getLetters();
        Rectangle[] shapes = current.getShapes();
        for (int i = 0; i < current.getSize(); i++) {
            int col = (int) shapes[i].getX() / BASE_GRID;
            int row = (int) shapes[i].getY() / BASE_GRID;
            letterBoard[row][col] = letters[i];
            visualGrid[row][col] = current.getCells()[i];
        }

        getNextPolyomino();
    }

    // Gets next polyomino in queue 
    public void getNextPolyomino() {
        System.out.println("getNextPolyomino called, queue size: " + polyominoQueue.size());
        if (!polyominoQueue.isEmpty()) {
            current = polyominoQueue.poll();
            System.out.println("Loaded piece: " + new String(current.getLetters()));
            for (int i = 0; i < current.getSize(); i++) {
                Rectangle block = current.getShapes()[i];
                block.setFill(javafx.scene.paint.Color.WHITE);
                block.setStroke(javafx.scene.paint.Color.YELLOW);
                block.setY(0);
                System.out.println("Block X:" + block.getX() + " Y:" + block.getY() + " W:" + block.getWidth() + " H:" + block.getHeight());
                visualBoard.getChildren().add(current.getCells()[i]);
            }
            System.out.println("After add, visualBoard children: " + visualBoard.getChildren().size());
        } else {
            System.out.println("Queue empty!");
        }
    }

    public Polyomino getCurrent() {
    return current;
}

    // Returns the visual board Pane 
    public Pane getVisualBoard() {
        return visualBoard;
    }

    public char[][] getLetterBoardCopy() {
        char[][] copy = new char[letterBoard.length][letterBoard[0].length];
        for (int r = 0; r < letterBoard.length; r++) {
            System.arraycopy(letterBoard[r],0,copy[r],0,letterBoard[r].length);
        }
        return copy;
    }
    public Polyomino getReserved() {
        return reserved;
    }
    public Polyomino peekNext() {
        return polyominoQueue.peek();
    }
}
