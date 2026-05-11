package com.wordris.wordrisproject;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Loads word banks from resource files
public class BankLoader {

    public static Set<String> loadStringSet(String resourcePath) {

        Set<String> words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to load bank: " + resourcePath, e
            );
        }
        return words;
    }
}
