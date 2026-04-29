package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

import java.util.Queue;

import static com.wordris.wordrisproject.Board.BASE_GRID;
import static com.wordris.wordrisproject.Board.X_MAX;

enum PolyominoState {
    PRE_SUFFIX, BASE;
}

enum formation {
    L,
    J,
    O,
    I,
    T,
    Z,
    S
}

public class PolyominoGenerator {
    private PolyominoState state = PolyominoState.PRE_SUFFIX;
    private formation form = formation.L;

    // TODO: implement a way to store all four letter string of prefixes and suffixes
    private final String[] prefixBank = new String[0];
    private final String[] suffixBank = new String[0];

    // TODO: implement the generator
    public Queue<Polyomino> generatePolyominos() {
        return null;
    }

    public void setForm(formation form) {
        this.form = form;
    }

    // TODO: implement the word bank, a way to get from either prefix or suffix String bank, and a random number generator to get a random String
    private String getStringBank() {
        String randomFour = prefixBank[0];
        return randomFour;
    }

    private Polyomino makePoly() {
        int blockSize = BASE_GRID;
        String chosenString = getStringBank();
        Rectangle[] polyParts = new Rectangle[chosenString.length()];

        for(int i = 0; i < chosenString.length(); i++) {
            polyParts[i] = new Rectangle(blockSize, blockSize);
        }

        // Each formation means setting x and y coordination will be different: b, c, and d are placed with reference to 'a' as the base
        switch(form) {
            case L:
                polyParts[0].setX(X_MAX / 2 + BASE_GRID);
                polyParts[1].setX(X_MAX / 2 - BASE_GRID);
                polyParts[1].setY(BASE_GRID);
                polyParts[2].setX(X_MAX / 2);
                polyParts[2].setY(BASE_GRID);
                polyParts[3].setX(X_MAX / 2 + BASE_GRID);
                polyParts[3].setY(BASE_GRID);
            case J:

        }

        return new Polyomino(chosenString, polyParts, chosenString.length());
    }
}
