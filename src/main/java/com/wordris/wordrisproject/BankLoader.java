package com.wordris.wordrisproject;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Loads word banks from resource files
public class BankLoader {

    public static Set<String> loadStringSet(String resourcePath) {

        Set<String> words = new HashSet<>();
        try (InputStream input = BankLoader.class.getResourceAsStream(resourcePath); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
