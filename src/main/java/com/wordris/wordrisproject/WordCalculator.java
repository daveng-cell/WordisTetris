package com.wordris.wordrisproject;
import java.util.*;

public class WordCalculator {
    private final Base_Bank baseBank;
    private final Prefix_Bank prefixBank;
    private final Suffix_Bank suffixBank;

    public WordCalculator(Base_Bank baseBank, Prefix_Bank prefixBank, Suffix_Bank suffixBank) {
        this.baseBank = baseBank;
        this.prefixBank = prefixBank;
        this.suffixBank = suffixBank;
    }

    public List<WordResult> checkForWords(Board board) {

        List<WordResult> foundWords = new ArrayList<>();

        char[][] grid = board.getLetterBoard();

        int rows = grid.length;
        int cols = grid[0].length;

        // scan horizontally CAN UPDATE BASED ON HOW WE SCAN FOR WORDS
        for (int row = 0; row < rows; row++) {

            StringBuilder currentWord = new StringBuilder();
            int startCol = 0;

            for (int col = 0; col < cols; col++) {

                char c = grid[row][col];

                if (c != '\0') {
                    if (currentWord.length() == 0) {
                        startCol = col;
                    }
                    currentWord.append(c);
                }
                // break sequence or end of row → evaluate
                if (c == '\0' || col == cols - 1) {

                    if (currentWord.length() > 0) {
                        evaluateSequence(
                                currentWord.toString(),
                                row,
                                startCol,
                                col - 1,
                                foundWords
                        );
                        currentWord.setLength(0);
                    }
                }
            }
        }

        return foundWords;
    }
   
  private void evaluateSequence(String sequence,
                                  int row,
                                  int startCol,
                                  int endCol,
                                  List<WordResult> results) {

        ParsedWord parsed = parseWord(sequence);

        if (isValid(parsed)) {

            int score = calculateScoreByWord(parsed);

            results.add(new WordResult(
                    parsed,
                    score,
                    row,
                    startCol,
                    endCol
            ));
        }
    } 
//word parsing 
private ParsedWord parseWord(String sequence) {

        List<String> prefixes = new ArrayList<>();
        List<String> suffixes = new ArrayList<>();
        String base = sequence;

        // --- PREFIX STRIP (left side greedy) ---
        boolean foundPrefix = true;

        while (foundPrefix) {
            foundPrefix = false;

            for (String p : prefixBank.getAll()) {
                if (base.startsWith(p)) {
                    prefixes.add(p);
                    base = base.substring(p.length());
                    foundPrefix = true;
                    break;
                }
            }
        }

        // --- SUFFIX STRIP (right side greedy) ---
        foundPrefix = true;

        while (foundPrefix) {
            foundPrefix = false;

            for (String s : suffixBank.getAll()) {
                if (base.endsWith(s)) {
                    suffixes.add(0, s);
                    base = base.substring(0, base.length() - s.length());
                    foundPrefix = true;
                    break;
                }
            }
        }

        return new ParsedWord(prefixes, base, suffixes);
    }

//validation by banks
    private boolean isValid(ParsedWord word) {

        if (!baseBank.contains(word.base)) {
            return false;
        }

        if (!prefixBank.isValidChain(word.prefixes)) {
            return false;
        }

        if (!suffixBank.isValidChain(word.suffixes)) {
            return false;
        }

        return true;
    }
    public int calculateScoreByWord(ParseWord word) {
        int score = 0;

        // base length
        score += word.base.length() * 2;

        // prefix complexity
        score += word.prefixes.size() * 3;

        // suffix complexity
        score += word.suffixes.size() * 4;

        // combo bonus
        if (!word.prefixes.isEmpty() && !word.suffixes.isEmpty()) {
            score += 5;
        }

        // chain bonus
        int complexity = word.prefixes.size() + word.suffixes.size();
        if (complexity >= 3) {
            score *= 1.5;
        }

        return score;
    }
}
