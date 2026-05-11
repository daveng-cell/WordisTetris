package com.wordris.wordrisproject;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Loads word banks from resource files
public class BankLoader {

    public static Set<String> loadStringSet(String resourcePath) {

        Set<String> words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to load bank: " + filePath, e
            );
        }
        return words;
    }
}
