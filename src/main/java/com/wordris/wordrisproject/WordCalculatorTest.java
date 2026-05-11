package com.wordris.wordrisproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCalculatorTest {

    private WordCalculator calculator;
    private Board board;

    @BeforeEach
    void setUp() {
        calculator = new WordCalculator();
        board = new Board();
    }

    // manuanky place word for test
    private void placeHorizontalWord(String word, int row, int colStart) {
        char[][] grid = board.getLetterBoardCopy();

        for (int i = 0; i < word.length(); i++) {
            grid[row][colStart + i] = word.charAt(i);
        }
    }

    // basic nor affix word test
    @Test
    void testBaseWordDetected() {
        placeHorizontalWord("run", 5, 2);
        List<WordResult> results = calculator.checkForWords(board);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")));
    }

    //single prefix test
    @Test
    void testPrefixWordDetected() {
        placeHorizontalWord("overrun", 4, 1);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")));
    }

    // single suffix test
    @Test
    void testSuffixWordDetected() {
        placeHorizontalWord("kissed", 3, 0);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("kiss")));
    }

    // prefix and suffix test
    @Test
    void testPrefixAndSuffixChainDetected() {
        placeHorizontalWord("rerunly", 2, 0);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")));
    }

    // invalid base test
    @Test
    void testInvalidWordRejected() {
        placeHorizontalWord("qzxq", 6, 0);
        List<WordResult> results = calculator.checkForWords(board);
        assertTrue(results.isEmpty());
    }

    //vertical scan test
    @Test
    void testVerticalWordDetected() {
        char[][] grid = board.getLetterBoardCopy();

        String word = "run";
        for (int i = 0; i < word.length(); i++) {
            grid[1 + i][3] = word.charAt(i);
        }

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")));
    }

    //affix bonus test
    @Test
    void testAffixScoreBonus() {
        placeHorizontalWord("overunly", 7, 0);

        List<WordResult> results = calculator.checkForWords(board);

        WordResult result = results.stream()
                .filter(r -> r.getParsedWord().getBase().equals("run"))
                .findFirst()
                .orElse(null);

        assertNotNull(result);

        // base + prefix + suffix: should be higher than base-only
        assertTrue(result.getScore() > 1);
    }
}
