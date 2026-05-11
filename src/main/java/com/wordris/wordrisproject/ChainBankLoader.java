package com.wordris.wordrisproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

// Helps with parsing Chain_Bank.txt
public class ChainBankLoader {
  
    public static void loadChains(String path, Set<String> prefixChains, Set<String> suffixChains) {

    try ( InputStream input = ChainBankLoader.class.getResourceAsStream(resourcePath); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
        boolean readingPrefixes = false;
        boolean readingSuffixes = false;
        String line;
        while ((line = reader.readLine()) != null) {
          line = line.trim();
          if (line.isEmpty()) {
            continue;
          }
          String normalized = line.toLowerCase();
          if (normalized.equals("prefix:")) {
          readingPrefixes = true;
          readingSuffixes = false;
          continue;
          }
          if (normalized.equals("suffix:")) {
          readingPrefixes = false;
          readingSuffixes = true;
          continue;
          }

          String cleaned = normalized.replace(" ", "");

          if (readingPrefixes) {
              prefixChains.add(cleaned);
          }

          else if (readingSuffixes) {
             suffixChains.add(cleaned);
          }
      }
    } catch (Exception e) { 
      throw new RuntimeException("Failed to load chain bank", e);
      }
    }
}
