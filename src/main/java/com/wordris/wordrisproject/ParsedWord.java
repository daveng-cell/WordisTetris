package com.wordris.wordrisproject;

import java.util.ArrayList;
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
    private final List<String> prefixes;
    private final String base;
    private final List<String> suffixes;
    public ParsedWord(List<String> prefixes, String base, List<String> suffixes) {
        this.prefixes = prefixes;
        this.base = base;
        this.suffixes = suffixes;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public String getBase() {
        return base;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    /*
     * Total number of affixes.
     */
    public int getAffixCount() {
        return prefixes.size() + suffixes.size();
    }
}
