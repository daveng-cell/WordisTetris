package com.wordris.wordrisproject;

/*
 * WordResult represents a VALID word found on the board.
 *
 * Stores:
 * - parsed word information
 * - score earned
 * - location on board
 * - orientation
 *
 * This class allows Board/GameManager
 * to know:
 * - where a word exists
 * - how many points it gives
 * - which tiles to remove
 */
public class WordResult {

    // Structured word information
    public ParsedWord word;

    // Score earned from this word
    public int score;

    /*
     * Coordinates:
     *
     * Horizontal:
     * fixedIndex = row
     * start/end = columns
     *
     * Vertical:
     * fixedIndex = column
     * start/end = rows
     */
    public int fixedIndex;
    public int start;
    public int end;

    // true = horizontal
    // false = vertical
    public boolean horizontal;

    /*
     * Constructor
     */
    public WordResult(ParsedWord word,
                      int score,
                      int fixedIndex,
                      int start,
                      int end,
                      boolean horizontal) {

        this.word = word;
        this.score = score;
        this.fixedIndex = fixedIndex;
        this.start = start;
        this.end = end;
        this.horizontal = horizontal;
    }
}
