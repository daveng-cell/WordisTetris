package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

// Helps with parsing Chain_Bank.txt
public class ChainBankLoader {
  
    public static void loadChains(
            String path,
            Set<String> prefixChains,
            Set<String> suffixChains) {

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(path))) {

            String line;

            boolean readingPrefix = false;
            boolean readingSuffix = false;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // PREFIX SECTION
                if (line.equalsIgnoreCase("PREFIX:")) {

                    readingPrefix = true;
                    readingSuffix = false;

                    continue;
                }

                // SUFFIX SECTION
                if (line.equalsIgnoreCase("SUFFIX:")) {

                    readingPrefix = false;
                    readingSuffix = true;

                    continue;
                }

                String normalized =
                        normalizeChain(line);

                if (readingPrefix) {
                    prefixChains.add(normalized);
                }

                if (readingSuffix) {
                    suffixChains.add(normalized);
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed loading chain bank."
            );
        }
    }
  
    private static String normalizeChain(
            String line) {

        String[] split = line.split(",");

        StringBuilder sb =
                new StringBuilder();

        for (int i = 0; i < split.length; i++) {

            sb.append(split[i].trim());

            if (i != split.length - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
