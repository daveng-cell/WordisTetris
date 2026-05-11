
package com.wordris.wordrisproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.wordris.wordrisproject.*;

import java.util.List;

class WordCalculatorTest {

    private WordCalculator calculator;
    private Board board;

    @BeforeEach
    void setUp() {
        calculator = new WordCalculator();
        board = new Board();
    }

    // manually place word for test
    private void placeHorizontalWord(String word, int row, int colStart) {
        for (int i = 0; i < word.length(); i++) {
            board.setLetter(row, colStart + i, word.charAt(i));
        }
    }

     private void placeVerticalWord(String word, int rowStart, int col) {
        for (int i = 0; i < word.length(); i++) {
            board.setLetter(rowStart + i, col, word.charAt(i));
        }
    }

    // basic no affix word test should not score
    @Test
    void testBaseWordRejectedWithoutAffix() {
        placeHorizontalWord("run", 5, 2);
        List<WordResult> results = calculator.checkForWords(board);
        assertTrue(results.isEmpty());
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
    placeVerticalWord("overrun", 1, 3);

    List<WordResult> results = calculator.checkForWords(board);

    assertTrue(results.stream()
        .anyMatch(r -> r.getParsedWord().getBase().equals("run")));
    }

    //affix bonus test
    @Test
    void testAffixScoreBonus() {
        placeHorizontalWord("overrunly", 7, 0);

        List<WordResult> results = calculator.checkForWords(board);

        WordResult result = results.stream().filter(r -> r.getParsedWord().getBase().equals("run")).findFirst().orElse(null);

        assertNotNull(result);

        // base + prefix + suffix: should be higher than base-only
        assertTrue(result.getScore() > 1);
    }
}
