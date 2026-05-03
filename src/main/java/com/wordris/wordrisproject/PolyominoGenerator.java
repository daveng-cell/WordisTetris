package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

import java.util.Queue;

import static com.wordris.wordrisproject.Board.BASE_GRID;
import static com.wordris.wordrisproject.Board.X_MAX;

enum PolyominoState {
    PRE_SUFFIX, BASE;
}

// Unsure of whether to do different shapes yet, will do straight lines first
//enum formation {
//    L,
//    J,
//    O,
//    I,
//    T,
//    Z,
//    S
//}

public class PolyominoGenerator {
    private PolyominoState state = PolyominoState.PRE_SUFFIX;

    // TODO: implement a way to store all four letter string of prefixes and suffixes
    private final String[] prefixAndSuffixBank = new String[0];
    private final String[] baseBank = new String[0];

    // TODO: implement the generator
    public Queue<Polyomino> generatePolyominos() {
        return null;
    }

//    public void setForm(formation form) {
//        this.form = form;
//    }

    // Requires JSON file or whatever kind of file with all prefixes, suffixes and bases to implement
    // Takes that file and implants them into the Strin banks
    private void createStringBank() {

    }

    // TODO: implement the word bank, a way to get from either prefix or suffix String bank, and a random number generator to get a random String
    private String getStringBank() {
        String random = prefixAndSuffixBank[0];
        return random;
    }

    private Polyomino makePoly() {
        int blockSize = BASE_GRID;
        String chosenString = getStringBank();
        Rectangle[] polyParts = new Rectangle[chosenString.length()];

        for(int i = 0; i < chosenString.length(); i++) {
            polyParts[i] = new Rectangle(blockSize, blockSize);
        }

        // Each formation means setting x and y coordination will be different: b, c, and d are placed with reference to 'a' as the base
        positionBlocks(polyParts);
        return new Polyomino(chosenString, polyParts, chosenString.length());
    }

    private void positionBlocks(Rectangle[] blocks) {
        // In an array of Rectangles, parts of a polyomino is placed as a straight line, similar to a crossword puzzle
        for(int i = 0; i < blocks.length; i++) {
            blocks[i].setX(X_MAX / 2 + (BASE_GRID * i));
        }
    }
}
