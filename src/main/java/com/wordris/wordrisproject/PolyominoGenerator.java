package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

import java.util.Queue;

import static com.wordris.wordrisproject.Board.BASE_GRID;
import static com.wordris.wordrisproject.Board.X_MAX;

enum PolyominoState {
    PREFIX, SUFFIX;
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
    private PolyominoState state = PolyominoState.PREFIX;
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

        Rectangle a = new Rectangle(blockSize, blockSize);
        Rectangle b = new Rectangle(blockSize, blockSize);
        Rectangle c = new Rectangle(blockSize, blockSize);
        Rectangle d = new Rectangle(blockSize, blockSize);

        // Each formation means setting x and y coordination will be different: b, c, and d are placed with reference to 'a' as the base
        switch(form) {
            case L:
                a.setX(X_MAX / 2 - BASE_GRID);
                b.setX(X_MAX / 2 - BASE_GRID);
                b.setY(BASE_GRID);
                c.setX(X_MAX / 2 - BASE_GRID);
                c.setY(BASE_GRID * 2);
                d.setX(X_MAX / 2 - (2 * BASE_GRID));
                d.setY(BASE_GRID * 2);
            case J:

        }

        Polyomino newPoly = new Polyomino(getStringBank(), a, b, c, d);
        return newPoly;
    }
}
