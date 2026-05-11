
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
    board.resetBoard();
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
        assertTrue(results.isEmpty(), "Base word no affix, should not be valid");
    }
    
    //single prefix test
    @Test
    void testPrefixWordDetected() {
        placeHorizontalWord("overrun", 4, 1);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")&& !r.getParsedWord().getPrefixes().isEmpty()), "Should detect base + prefix");
    }

    // single suffix test
    @Test
    void testSuffixWordDetected() {
        placeHorizontalWord("kissed", 3, 0);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("kiss") && !r.getParsedWord().getSuffixes().isEmpty()), "Should detect base + suffix");
    }

    // prefix and suffix test
    @Test
    void testPrefixAndSuffixChainDetected() {
        placeHorizontalWord("rerunly", 2, 0);

        List<WordResult> results = calculator.checkForWords(board);

        assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run")), "should detect base inside prefix and suffix chain");
    }

    // invalid base test
    @Test
    void testInvalidWordRejected() {
        placeHorizontalWord("qzxq", 6, 0);
        List<WordResult> results = calculator.checkForWords(board);
        assertTrue(results.isEmpty(), "invalid word, no results expected");
    }

    //vertical scan test
    @Test
    void testVerticalWordDetected() {
    placeVerticalWord("overrun", 1, 3);

    List<WordResult> results = calculator.checkForWords(board);

    assertTrue(results.stream().anyMatch(r -> r.getParsedWord().getBase().equals("run") && !r.isHorizontal()), "vert word should be detected");
    }

    //affix bonus test
    @Test
    void testAffixScoreBonus() {
    placeHorizontalWord("overrunly", 7, 0);

    List<WordResult> results = calculator.checkForWords(board);

    WordResult result = results.stream().filter(r -> r.getParsedWord().getBase().equals("run")).findFirst().orElse(null);

    assertNotNull(result, "Word should be detected");

    // Must have at least 2 affixes (prefix + suffix)
    assertEquals(2, result.getParsedWord().getAffixCount(), "Expected exactly 2 affixes (prefix + suffix)");

    // Score must reflect affix-based scoring system
    assertTrue(result.getScore() >= 2, "Score should match affix-based scoring rules");
    }
}
