package com.wordris.wordrisproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Responsible ONLY for parsing words
// Longest-match + backtracking parsing
public class WordParser {

    private final Set<String> prefixBank;
    private final Set<String> suffixBank;
    private final Set<String> baseBank;

    private final List<String> sortedPrefixes;
    private final List<String> sortedSuffixes;

    public WordParser(AffixBank bank) {
        this.prefixBank = bank.getPrefixes();
        this.suffixBank = bank.getSuffixes();
        this.baseBank = bank.getBases();

        sortedPrefixes = new ArrayList<>(prefixBank);
        sortedSuffixes = new ArrayList<>(suffixBank);

        sortedPrefixes.sort((a, b) -> Integer.compare(b.length(), a.length()));
        sortedSuffixes.sort((a, b) -> Integer.compare(b.length(), a.length()));
    }

    // ENTRY POINT
    public ParsedWord parseWord(String word) {
        if (word == null) {
            return new ParsedWord(new ArrayList<>(), "", new ArrayList<>());
        }

        word = word.toLowerCase();
        ParsedWord result = parseRecursive(word, new ArrayList<>(), new ArrayList<>());

        // fallback
        if (result == null) {
            return new ParsedWord(new ArrayList<>(), word, new ArrayList<>());
        }

        return result;
    }

    // RECURSIVE BACKTRACKING PARSER
    private ParsedWord parseRecursive(
            String working,
            List<String> prefixes,
            List<String> suffixes
    ) {

        // SUCCESS CONDITION
        if (baseBank.contains(working)) {
            return new ParsedWord( new ArrayList<>(prefixes), working, new ArrayList<>(suffixes));
        }

        // Try PREFIXES
        for (String prefix : sortedPrefixes) {
            if (working.startsWith(prefix)) {

                prefixes.add(prefix);

                ParsedWord result = parseRecursive( working.substring(prefix.length()), prefixes, suffixes);

                prefixes.remove(prefixes.size() - 1);

                if (result != null) {
                    return result;
                }
            }
        }

        // Try SUFFIXES
        for (String suffix : sortedSuffixes) {
            if (working.endsWith(suffix)) {

                suffixes.add(suffix);

                ParsedWord result = parseRecursive( working.substring(0, working.length() - suffix.length()), prefixes, suffixes);

                suffixes.remove(suffixes.size() - 1);

                if (result != null) {
                    return result;
                }
            }
        }

        // FAILURE
        return null;
    }
}
