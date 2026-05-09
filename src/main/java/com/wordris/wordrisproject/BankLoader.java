package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//loading files into Sets
public class BankLoader {

    /*
     * Loads every non-empty line
     * into a Set<String>.
     */
    public static Set<String> loadStringSet(
            String path) {

        Set<String> result =
                new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(path))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    result.add(line);
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed loading file: " + path
            );
        }

        return result;
    }
}
