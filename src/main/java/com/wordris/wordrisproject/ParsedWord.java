package com.wordris.wordrisproject;

import java.util.List;

/*
 * ParsedWord stores the STRUCTURE of a detected word.
 *
 * Example:
 * "replayablely"
 *
 * prefixes = ["re"]
 * base = "play"
 * suffixes = ["able", "ly"]
 *
 * This allows WordCalculator to validate
 * prefixes/suffixes separately from the base word.
 */
public class ParsedWord {

    // Ordered list of prefixes detected
    public List<String> prefixes;

    // Core base word
    public String base;

    // Ordered list of suffixes detected
    public List<String> suffixes;

    /*
     * Constructor
     */
    public ParsedWord(List<String> prefixes,
                      String base,
                      List<String> suffixes) {

        this.prefixes = prefixes;
        this.base = base;
        this.suffixes = suffixes;
    }
}
