package com.wordris.wordrisproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Scans board, coordinates parser/validator/scorer
public class WordCalculator {

    private final WordParser parser;
    private final WordValidator validator;
    private final ScoreCalculator scorer;

    public WordCalculator() {
        // Load banks
        var bases = BankLoader.loadStringSet("/com/wordris/wordrisproject/Base_Bank");
        var prefixes = BankLoader.loadStringSet("/com/wordris/wordrisproject/Prefix_Bank");
        var suffixes = BankLoader.loadStringSet("/com/wordris/wordrisproject/Suffix_Bank");

        var prefixChains = new HashSet<String>();
        var suffixChains = new HashSet<String>();
        ChainBankLoader.loadChains("/com/wordris/wordrisproject/Chain_Bank", prefixChains, suffixChains);

        // Bank object
        AffixBank bank = new AffixBank( bases, prefixes, suffixes, prefixChains, suffixChains);

        // Helpers which WordCalculator delegates tasks to
        parser = new WordParser(bank);
        validator = new WordValidator(bank);
        scorer = new ScoreCalculator();
    }

    // Scans board
    public List<WordResult> checkForWords(Board board) {
        List<WordResult> results = new ArrayList<>();
        char[][] grid = board.getLetterBoardCopy();

        scanHorizontal(grid, results);
        scanVertical(grid, results);
        return results;
    }

    // Horizontal scan
    private void scanHorizontal(char[][] grid, List<WordResult> results) {
        for (int row = 0; row < grid.length; row++) {
            StringBuilder sb = new StringBuilder();
            int startCol = -1;
            for (int col = 0; col < grid[0].length; col++) {
                char c = grid[row][col];
                if (c != '\0') {
                    if (sb.isEmpty()) {
                        startCol = col;
                    }
                    sb.append(c);
                } else {
                    evaluateSequence(sb,row,startCol,col - 1,true,results);
                }
            }
            evaluateSequence(sb,row,startCol,grid[0].length - 1,true,results);
        }
    }

    // Vertical scan
    private void scanVertical(char[][] grid,List<WordResult> results) {
        for (int col = 0; col < grid[0].length; col++) {
            StringBuilder sb = new StringBuilder();

            int startRow = -1;
            for (int row = 0; row < grid.length; row++) {
                char c = grid[row][col];
                if (c != '\0') {
                    if (sb.isEmpty()) {
                        startRow = row;
                    }
                    sb.append(c);
                } else {
                    evaluateSequence(sb, col, startRow, row - 1, false, results);
                }
            }

            evaluateSequence(sb,col,startRow,grid.length - 1,false,results);
        }
    }

    // Word evaluation
    private void evaluateSequence(StringBuilder sb, int fixedIndex, int start, int end, boolean horizontal, List<WordResult> results) {
        if (sb.isEmpty()) {
            return;
        }
        String word = sb.toString();
        sb.setLength(0);
        ParsedWord parsed = parser.parseWord(word);
        if (validator.isValid(parsed)) {
            int score = scorer.calculateScore(parsed);
            results.add(new WordResult(parsed,score,horizontal,fixedIndex,start,end));
        }
    }
}
