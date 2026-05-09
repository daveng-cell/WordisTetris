package com.wordris.wordrisproject;

//stores valid words to be assigned points by wordcalculator
public class WordResult {

    private final ParsedWord parsedWord;

    private final int score;
    private final boolean horizontal;
    /*
     * If horizontal:
     * fixedIndex = row
     * start/end = columns
     *
     * If vertical:
     * fixedIndex = column
     * start/end = rows
     */
    private final int fixedIndex;
    private final int start;
    private final int end;
    public WordResult(ParsedWord parsedWord, int score, boolean horizontal, int fixedIndex, int start, int end) {
        this.parsedWord = parsedWord;
        this.score = score;
        this.horizontal = horizontal;
        this.fixedIndex = fixedIndex;
        this.start = start;
        this.end = end;
    }
    
    public ParsedWord getParsedWord() {
        return parsedWord;
    }

    public int getScore() {
        return score;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getFixedIndex() {
        return fixedIndex;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
