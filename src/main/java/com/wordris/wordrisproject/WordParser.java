package com.wordris.wordrisproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Responsible ONLY for parsing words
// Longest-match parsing
public class WordParser {
    // Banks used during parsing
    private final Set<String> prefixBank;
    private final Set<String> suffixBank;
    private final Set<String> baseBank;

    //sorted list of affixes for ease of parsing
    private final List<String> sortedPrefixes;
    private final List<String> sortedSuffixes;

    public WordParser(AffixBank bank) {
        this.prefixBank = bank.getPrefixes();
        this.suffixBank = bank.getSuffixes();
        this.baseBank = bank.getBases();

        //convert set to list
        sortedPrefixes = new ArrayList<>(prefixBank);
        sortedSuffixes = new ArrayList<>(suffixBank);
        //longest checked first
        sortedPrefixes.sort((a, b) -> b.length() - a.length());
        sortedSuffixes.sort((a, b) -> b.length() - a.length());
    }

    public parseWord(String word){
        ParsedWord result = parseRecursive(word, new ArrayList<>(), new ArrayList<>());
        //if parsing fails
        if(result == null){
            return new ParsedWord(new ArrayList<>(), word, new ArratList<>());
        }
        return result;
    }
    //parsing resursivly 
    private ParsedWord parseRecursive(String working, List<String> prefixes, List<String> suffixes) {
        // success condition
        if (baseBank.contains(working)) {
            return new ParsedWord( new ArrayList<>(prefixes), working, new ArrayList<>(suffixes));
        }
        // try prefixes longest first
        for (String prefix : sortedPrefixes) {
            if (working.startsWith(prefix) && working.length() > prefix.length()) {
                prefixes.add(prefix);
                ParsedWord result = parseRecursive( working.substring(prefix.length()), prefixes, suffixes);
                //backtracking
                prefixes.remove(prefixes.size() - 1);
                if (result != null) {
                    return result;
                }
            }
        }
        //try suffixes longest first
        for (String suffix : sortedSuffixes) {

            if (working.endsWith(suffix) && working.length() > suffix.length()) {
                suffixes.add(0, suffix);
                ParsedWord result = parseRecursive( working.substring( 0, working.length() - suffix.length()), prefixes, suffixes);
               
                //backtracking
                suffixes.remove(0);
                if (result != null) {
                    return result;
                }
            }
        }
        // no valid parse found
        return null;
    }
}
