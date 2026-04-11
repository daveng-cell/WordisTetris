package com.wordris.wordrisproject;

import java.util.Queue;

public class Board {
    private char[][] board;
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
