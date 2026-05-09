package com.wordris.wordrisproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Responsible ONLY for parsing words
public class WordParser {

    private final Set<String> prefixBank;

    private final Set<String> suffixBank;

    public WordParser(AffixBank bank) {
        this.prefixBank = bank.getPrefixes();
        this.suffixBank = bank.getSuffixes();
    }

    public ParsedWord parseWord(String word) {

        List<String> prefixes = new ArrayList<>();

        List<String> suffixes = new ArrayList<>();

        String working = word;
      //prefix parsing
        boolean found = true;
        while (found) {
            found = false;
            for (String prefix : prefixBank) {
                if (working.startsWith(prefix)) {
                    prefixes.add(prefix);
                    working = working.substring(prefix.length());
                    found = true;
                    break;
                }
            }
        }

        // suffix parsing
        found = true;
        while (found) {
            found = false;
            for (String suffix : suffixBank) {
                if (working.endsWith(suffix)) {
                    suffixes.add(0, suffix);
                    working = working.substring(0,working.length() - suffix.length());
                    found = true;
                    break;
                }
            }
        }

        return new ParsedWord(prefixes,working,suffixes);
    }
}
