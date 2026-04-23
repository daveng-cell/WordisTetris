package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

public class Polyomino {
    // letters stores the specific letters on the polyomino
    // shapes store the specific formation of the polyomino
    private final char[] letters = new char[4];
    private final Rectangle[] shapes = new Rectangle[4];

    // TODO: implement setup for coordinates of polyomino, as well as characters on rectangles
    public Polyomino(String characters, Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        // Polyominoes only have four blocks
        for(int i = 0; i < 4; i++) {
            letters[i] = characters.charAt(i);
        }

        shapes[0] = a;
        shapes[1] = b;
        shapes[2] = c;
        shapes[3] = d;
    }

}
