package com.wordris.wordrisproject;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Polyomino {
    private final char[] letters;
    private final Rectangle[] shapes;
    private final Group[] cells;

    public Polyomino(String characters, Rectangle[] blocks, int size) {
        letters = new char[size];
        shapes = new Rectangle[size];
        cells = new Group[size];

        for (int i = 0; i < size; i++) {
            letters[i] = characters.charAt(i);
            shapes[i] = blocks[i];

            Text text = new Text(String.valueOf(characters.charAt(i)).toUpperCase());
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

            text.xProperty().bind(
                blocks[i].xProperty().add(blocks[i].getWidth() / 2 - 5)
            );
            text.yProperty().bind(
                blocks[i].yProperty().add(blocks[i].getHeight() / 2 + 5)
            );

            cells[i] = new Group(blocks[i], text);
        }
    }

    public char[] getLetters() { return letters; }
    public Rectangle[] getShapes() { return shapes; }
    public Group[] getCells() { return cells; }
    public int getSize() { return letters.length; }
}