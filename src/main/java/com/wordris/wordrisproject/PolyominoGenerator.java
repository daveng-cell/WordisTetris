package com.wordris.wordrisproject;

import javafx.scene.shape.Rectangle;

import java.util.*;

import static com.wordris.wordrisproject.Board.BASE_GRID;
import static com.wordris.wordrisproject.Board.X_MAX;

enum PolyominoState {
    PREFIX, SUFFIX, BASE
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
    private final ArrayList<String> prefixBank = new ArrayList<>();
    private final ArrayList<String> suffixBank = new ArrayList<>();
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
            if (state == PolyominoState.PREFIX) {
                setState(PolyominoState.BASE);
            }
            else if (state == PolyominoState.BASE) {
                setState(PolyominoState.SUFFIX);
            }
            else {
                setState(PolyominoState.PREFIX);
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
        Set<String> tempSet;
        tempSet = BankLoader.loadStringSet("src/main/java/com/wordris/wordrisproject/Prefix_Bank");
        Objects.requireNonNull(prefixBank).addAll(tempSet);

        tempSet = BankLoader.loadStringSet("src/main/java/com/wordris/wordrisproject/Suffix_Bank");
        Objects.requireNonNull(suffixBank).addAll(tempSet);

        tempSet = BankLoader.loadStringSet("src/main/java/com/wordris/wordrisproject/Base_Bank");
        Objects.requireNonNull(baseBank).addAll(tempSet);

    }

    private String getRandomString() {
        Random r = new Random();
        int bound;
        ArrayList<String> currBank = switch (state) {
            case PREFIX -> {
                bound = prefixBank.size();
                yield prefixBank;
            }
            case SUFFIX -> {
                bound = suffixBank.size();
                yield suffixBank;
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
