package com.wordris.wordrisproject;

import java.util.List;

// Responsible ONLY for validating parsed words.
public class WordValidator {

    private final AffixBank bank;

    public WordValidator(
            AffixBank bank) {

        this.bank = bank;
    }

    /*
     * Rules:
     *
     * 1. Base must exist
     * 2. Must contain at least 1 affix
     * 3. Prefix chains valid
     * 4. Suffix chains valid
     */
    public boolean isValid(ParsedWord word) {
        // Base must exist
        if (!bank.getBases().contains(word.getBase())) {

            return false;
        }

        // Must contain affix
        if (word.getAffixCount() == 0) {
            return false;
        }

        // Prefix validation
        if (!validPrefixChain(word.getPrefixes())) {
            return false;
        }

        // Suffix validation
        return validSuffixChain(word.getSuffixes());
    }

    private boolean validPrefixChain(List<String> prefixes) {
        if (prefixes.isEmpty()) {
            return true;
        }

        // Single prefix
        if (prefixes.size() == 1) {
            return bank.getPrefixes().contains(prefixes.get(0));
        }

        // Multiple prefixes
        String chain = String.join(",", prefixes).replaceAll("\\s+", "");

        return bank.getPrefixChains().contains(chain);
    }

    private boolean validSuffixChain(List<String> suffixes) {
        if (suffixes.isEmpty()) {
            return true;
        }

        // Single suffix
        if (suffixes.size() == 1) {
          return bank.getSuffixes().contains(suffixes.get(0));
        }

        // Multiple suffixes
        String chain = String.join(",", suffixes).replaceAll("\\s+", "");
        return bank.getSuffixChains().contains(chain);
    }
}
