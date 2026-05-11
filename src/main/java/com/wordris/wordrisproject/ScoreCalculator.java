package com.wordris.wordrisproject;

// Score calculation
public class ScoreCalculator {
    // Every affix gives 1 point
    private static final int AFFIX_POINTS = 1;
    // Combo bonuses
    private static final int THREE_AFFIX_BONUS = 3;
    private static final int FOUR_AFFIX_BONUS = 6;
    private static final int FIVE_PLUS_AFFIX_BONUS = 10;

    // Calculates score based on num of affixes
    public int calculateScore(
            ParsedWord word) {

        int affixes = word.getAffixCount();
        int score = affixes * AFFIX_POINTS;

        // Combo bonuses
        if (affixes == 3) {
          score += THREE_AFFIX_BONUS;
        } else if (affixes == 4) {
            score += FOUR_AFFIX_BONUS;
        } else if (affixes >= 5) {
            score += FIVE_PLUS_AFFIX_BONUS;
        }
        return score;
    }
}
