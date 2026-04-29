package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

public class Polyomino {
    // letters stores the specific letters on the polyomino
    // shapes store the specific formation of the polyomino
    private char[] letters;
    private Rectangle[] shapes;

    // TODO: implement setup for coordinates of polyomino, as well as characters on rectangles
    public Polyomino(String characters, Rectangle[] blocks, int size) {
        letters = new char[size];
        shapes = new Rectangle[size];

        for(int i = 0; i < size; i++) {
            letters[i] = characters.charAt(i);
            shapes[i] = blocks[i];
        }
    }
}
