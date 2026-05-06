package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BankLoader {

    // LOAD in simple string sets
    public static Set<String> loadStringSet(String filePath) {

        Set<String> set = new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;

                set.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return set;
    }

    // Chain bank load
    public static Map<String, Set<List<String>>> loadChainBank(String filePath) {

        Map<String, Set<List<String>>> map = new HashMap<>();

        map.put("PREFIX", new HashSet<>());
        map.put("SUFFIX", new HashSet<>());

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;
            String currentSection = "";

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;

                if (line.equalsIgnoreCase("PREFIX:")) {
                    currentSection = "PREFIX";
                    continue;
                }

                if (line.equalsIgnoreCase("SUFFIX:")) {
                    currentSection = "SUFFIX";
                    continue;
                }

                String[] split = line.split(",");

                List<String> chain = new ArrayList<>();

                for (String s : split) {
                    chain.add(s.trim());
                }

                map.get(currentSection).add(chain);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
}
