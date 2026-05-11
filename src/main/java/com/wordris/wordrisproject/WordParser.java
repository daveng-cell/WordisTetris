package com.wordris.wordrisproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Responsible ONLY for parsing words
//longest-match parsing
public class WordParser {

    // Banks used during parsing
    private final Set<String> prefixBank;
    private final Set<String> suffixBank;
    private final Set<String> baseBank;

    public WordParser(AffixBank bank) {
        this.prefixBank = bank.getPrefixes();
        this.suffixBank = bank.getSuffixes();
        this.baseBank = bank.getBases();
    }

    // Parses a word into:prefixes + base + suffixes
    public ParsedWord parseWord(String word) {

        List<String> prefixes = new ArrayList<>();
        List<String> suffixes = new ArrayList<>();
        String working = word;

        //prefix parsing
        boolean found = true;

        while (found) {
            found = false;
            String bestMatch = null;
            
            // Longest-match prefix search
            for (String prefix : prefixBank) {
                if (working.startsWith(prefix)) {
                    if (bestMatch == null || prefix.length() > bestMatch.length()) {
                        bestMatch = prefix;
                    }
                }
            }

            // Remove best prefix
            if (bestMatch != null) {
                prefixes.add(bestMatch);
                working = working.substring(bestMatch.length());
                found = true;
            }
        }

        //suffix parsing
        found = true;
        while (found) {
            // !!!!stop stripping once remaining word is valid base
            if (baseBank.contains(working)) {
                break;
            }
            found = false;
            String bestMatch = null;
            // Longest-match suffix search
            for (String suffix : suffixBank) {
                if (working.endsWith(suffix)) {
                    if (bestMatch == null || suffix.length() > bestMatch.length()) {
                        bestMatch = suffix;
                    }
                }
            }

            // Remove best suffix
            if (bestMatch != null) {
                suffixes.add(0, bestMatch);
                working = working.substring(0, working.length() - bestMatch.length());
                found = true;
            }
        }

        return new ParsedWord(prefixes, working, suffixes);
    }
}
