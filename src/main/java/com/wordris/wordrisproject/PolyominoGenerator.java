package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import static com.wordris.wordrisproject.Board.BASE_GRID;
import static com.wordris.wordrisproject.Board.X_MAX;

enum PolyominoState {
    PRE_SUFFIX, BASE
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
    private PolyominoState state;
    private final ArrayList<String> prefixAndSuffixBank = new ArrayList<>();
    private final ArrayList<String> baseBank = new ArrayList<>();

    PolyominoGenerator(PolyominoState state) {
        this.state = state;
        createStringBank();
    }

    // May need improvements
    public ArrayDeque<Polyomino> generatePolyominos(int numOfPolyominoes) {
        ArrayDeque<Polyomino> currQueue = new ArrayDeque<>();

        for(int i = 0; i < numOfPolyominoes; i++) {
            currQueue.add(makePoly());
            if(state == PolyominoState.PRE_SUFFIX) {
                setState(PolyominoState.BASE);
            }
            else {
                setState(PolyominoState.PRE_SUFFIX);
            }
        }
        return currQueue;
    }

    private void setState(PolyominoState state) {
        this.state = state;
    }

    // Requires file with all prefixes, suffixes and bases to implement
    // Takes that file and implants them into the Strin banks
    private void createStringBank() {
        try {
            BufferedReader prefix_br = new BufferedReader(new FileReader("Prefix_Bank"));
            BufferedReader suffix_br = new BufferedReader(new FileReader("Suffix_Bank"));
            BufferedReader base_br = new BufferedReader(new FileReader("Base_Bank"));
            String line;

            while((line = prefix_br.readLine()) != null) {
                prefixAndSuffixBank.add(line);
            }
            while((line = suffix_br.readLine()) != null) {
                prefixAndSuffixBank.add(line);
            }
            while((line = base_br.readLine()) != null) {
                baseBank.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRandomString() {
        Random r = new Random();
        int bound;
        ArrayList<String> currBank = switch (state) {
            case PRE_SUFFIX -> {
                bound = prefixAndSuffixBank.size();
                yield prefixAndSuffixBank;
            }
            case BASE -> {
                bound = baseBank.size();
                yield baseBank;
            }
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };

        return currBank.get(r.nextInt(bound));
    }

    private Polyomino makePoly() {
        int blockSize = BASE_GRID;
        String chosenString = getRandomString();
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
            blocks[i].setX((X_MAX / 2) + (BASE_GRID * i));
        }
    }
}
