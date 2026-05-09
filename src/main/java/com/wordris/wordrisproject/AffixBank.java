package com.wordris.wordrisproject;

import java.util.Set;

/*
 * Stores all loaded word banks.
 *
 */
public class AffixBank {

    // All valid base words
    private final Set<String> bases;

    // All valid single prefixes
    private final Set<String> prefixes;

    // All valid single suffixes
    private final Set<String> suffixes;

    // Valid prefix chains
    // Example:
    // "re,over"
    private final Set<String> prefixChains;

    // Valid suffix chains
    // Example:
    // "ful,ness,ly"
    private final Set<String> suffixChains;

    public AffixBank(
            Set<String> bases,
            Set<String> prefixes,
            Set<String> suffixes,
            Set<String> prefixChains,
            Set<String> suffixChains) {

        this.bases = bases;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        this.prefixChains = prefixChains;
        this.suffixChains = suffixChains;
    }

    public Set<String> getBases() {
        return bases;
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }

    public Set<String> getSuffixes() {
        return suffixes;
    }

    public Set<String> getPrefixChains() {
        return prefixChains;
    }

    public Set<String> getSuffixChains() {
        return suffixChains;
    }
}
