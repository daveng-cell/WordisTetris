package com.wordris.wordrisproject;

import java.util.Queue;

enum PolyominoState {
    PREFIX, SUFFIX;
}

public class PolyominoGenerator {
    PolyominoState state = PolyominoState.PREFIX;
    String[] prefixBank;
    String[] suffixBank;

    // TODO: implement the generator
    public Queue<Polyomino> generatePolyominos() {
        return null;
    }
}
