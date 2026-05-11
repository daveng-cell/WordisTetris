package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

// Helps with parsing Chain_Bank.txt
public class ChainBankLoader {

    public static void loadChains(String path, Set<String> prefixChains, Set<String> suffixChains) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            boolean readingPrefixes = false;
            boolean readingSuffixes = false;
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equalsIgnoreCase("PREFIX:")) {
                    readingPrefixes = true;
                    readingSuffixes = false;
                    continue;
                }

                if (line.equalsIgnoreCase("SUFFIX")) {
                    readingPrefixes = false;
                    readingSuffixes = true;
                    continue;
                }
                if (line.isEmpty()) continue;
                if (readingPrefixes) {
                    prefixChains.add(line);
                } else if (readingSuffixes) {
                    suffixChains.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load chain bank" + path, e);
        }
    }
}
