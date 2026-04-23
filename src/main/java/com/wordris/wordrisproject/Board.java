package com.wordris.wordrisproject;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.Queue;

public class Board {
    // Global variables: BASE_GRID represents the size of a 1x1 space in a grid
    static final int BASE_GRID = 25;
    static final int X_MAX = BASE_GRID * 12;
    static final int Y_MAX = BASE_GRID * 24;

    // letterBoard holds the data for what char is in which coordinate of the grid for the board
    // visualBoard is the visual of the board as a pane
    private final char[][] letterBoard = new char[X_MAX / BASE_GRID][Y_MAX / BASE_GRID];
    private final Pane visualBoard = new Pane();
    private final Scene scene = new Scene(visualBoard, X_MAX, Y_MAX);

    // These items are separate from the board, but interact with the board in some way
    private Polyomino current;
    private Polyomino reserved;
    private Queue<Polyomino> polyominoQueue;
    private WordCalculator wordCalculator;

    // TODO: return values can be changed later down the line
    //  returns boolean values to see if each function went through successfully or not
    public boolean resetBoard() {
        return false;
    }

    //  TODO: implement a way to remove a certain amount of blocks from one coordinate to another, then push any blocks above downward
    //   returns boolean values to see if each function went through successfully or not
    public boolean removeWord(int xFrom, int yFrom, int xTo, int yTo) {
        return false;
    }

    //  TODO: implement a way to move the controllable polyomino
    //   just an idea, -1 = left, 1 = right, no moving up or down
    public void moveCurrentPolyomino(int direction) {

    }

    // TODO: implement a way to switch the current controllable block with the reserved block
    public void switchReservedPolyomino() {

    }

    // TODO: implement a way to place a block, from any height down to the lowest possible depth
    public void placeCurrentPolyomino() {

    }

    public void getNextPolyomino() {

    }
}
