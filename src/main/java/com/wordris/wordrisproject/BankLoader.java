package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.io.IOException;
import java.util.*;

public class BankLoader {
    // LOAD in simple string sets
    public static Set<String> loadStringSet(String path) {
        Set<String> result = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed loading file: " + path);
        }
        return result;
    }
}
