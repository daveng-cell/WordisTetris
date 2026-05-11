package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

// Helps with parsing Chain_Bank.txt
public class ChainBankLoader {
  
    public static void loadChains(String path, Set<String> prefixChains, Set<String> suffixChains) {

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        boolean readingPrefixes = false;
        boolean readingSuffixes = false;
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equalsIgnoreCase("PREFIX:")) {
              readingPrefix = true;
              readingSuffix = false;
              continue;
            }

            if (line.equalsIgnoreCase("SUFFIX")) {
                readingPrefix = false;
                readingSuffix = true;
                continue;
            }
          if (line.isEmpty()) continue;
          if (readingPrefix) {
            prefixChains.add(line);
          } else if (readingSuffix) {
            suffixChains.add(line);
          }
        }
    } catch (Exception e) { 
      throw new RuntimeException("Failed to load chain bank" + filePath, e);
      }
    }
}
