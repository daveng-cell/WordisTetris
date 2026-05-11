package com.wordris.wordrisproject;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    // Initialize JavaFX toolkit before tests or ignore if initialized
    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {}
    }

    // Reset board logic 
    @Test
    void resetBoard_clearsLetterBoard() {
        Platform.runLater(() -> {
            // Place some letters manually
            board.setLetter(0, 0, 'a');
            board.setLetter(5, 3, 'z');

            board.resetBoard();

            char[][] copy = board.getLetterBoardCopy();
            for (int row = 0; row < copy.length; row++) {
                for (int col = 0; col < copy[row].length; col++) {
                    assertEquals('\0', copy[row][col],
                        "Expected empty cell at [" + row + "][" + col + "]");
                }
            }
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void resetBoard_returnsTrue() {
        Platform.runLater(() -> {
            assertTrue(board.resetBoard());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void resetBoard_clearsCurrentAndReserved() {
        Platform.runLater(() -> {
            board.resetBoard();
            assertNull(board.getReserved());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Remove word logic

    @Test
    void removeWord_clearsCorrectCells() {
        Platform.runLater(() -> {
            int row = 10;
            board.setLetter(row, 3, 'r');
            board.setLetter(row, 4, 'u');
            board.setLetter(row, 5, 'n');

            board.removeWord(
                3 * Board.BASE_GRID, row * Board.BASE_GRID,
                5 * Board.BASE_GRID, row * Board.BASE_GRID
            );

            char[][] copy = board.getLetterBoardCopy();
            assertEquals('\0', copy[row][3]);
            assertEquals('\0', copy[row][4]);
            assertEquals('\0', copy[row][5]);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void removeWord_returnsTrue_onValidInput() {
        Platform.runLater(() -> {
            int row = 5;
            board.setLetter(row, 2, 'a');
            board.setLetter(row, 3, 'b');

            assertTrue(board.removeWord(
                2 * Board.BASE_GRID, row * Board.BASE_GRID,
                3 * Board.BASE_GRID, row * Board.BASE_GRID
            ));
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void removeWord_returnsFalse_onOutOfBounds() {
        Platform.runLater(() -> {
            assertFalse(board.removeWord(-1, 0, 100, 0));
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void removeWord_returnsFalse_onDifferentRows() {
        Platform.runLater(() -> {
            // rowFrom != rowTo should return false
            assertFalse(board.removeWord(
                0, 0 * Board.BASE_GRID,
                0, 1 * Board.BASE_GRID
            ));
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void removeWord_shiftsBlocksDown() {
        Platform.runLater(() -> {
            // Place a letter above the row being cleared
            board.setLetter(4, 3, 'x');
            board.setLetter(5, 3, 'y');

            // Remove row 5
            board.removeWord(
                3 * Board.BASE_GRID, 5 * Board.BASE_GRID,
                3 * Board.BASE_GRID, 5 * Board.BASE_GRID
            );

            char[][] copy = board.getLetterBoardCopy();
            // 'x' from row 4 should have shifted down to row 5
            assertEquals('x', copy[5][3]);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // moveCurrentPolyomino method

    @Test
    void moveCurrentPolyomino_movesLeft() {
        Platform.runLater(() -> {
            Polyomino current = board.getCurrent();
            double originalX = current.getShapes()[0].getX();

            board.moveCurrentPolyomino(-1);

            assertEquals(originalX - Board.BASE_GRID, current.getShapes()[0].getX());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void moveCurrentPolyomino_movesRight() {
        Platform.runLater(() -> {
            Polyomino current = board.getCurrent();
            double originalX = current.getShapes()[0].getX();

            board.moveCurrentPolyomino(1);

            assertEquals(originalX + Board.BASE_GRID, current.getShapes()[0].getX());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void moveCurrentPolyomino_blockedByLeftWall() {
        Platform.runLater(() -> {
            // Move all the way left
            for (int i = 0; i < 20; i++) {
                board.moveCurrentPolyomino(-1);
            }
            // First block should not go below x=0
            double x = board.getCurrent().getShapes()[0].getX();
            assertTrue(x >= 0);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void moveCurrentPolyomino_blockedByRightWall() {
        Platform.runLater(() -> {
            for (int i = 0; i < 20; i++) {
                board.moveCurrentPolyomino(1);
            }
            // Last block should not exceed X_MAX
            Rectangle[] shapes = board.getCurrent().getShapes();
            double rightmostX = shapes[shapes.length - 1].getX() + Board.BASE_GRID;
            assertTrue(rightmostX <= Board.X_MAX);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Placing current Polyomino

    @Test
    void placeCurrentPolyomino_locksToBottom() {
        Platform.runLater(() -> {
            Polyomino current = board.getCurrent();
            board.placeCurrentPolyomino();

            char[][] copy = board.getLetterBoardCopy();
            int bottomRow = (Board.Y_MAX / Board.BASE_GRID) - 1;

            // At least one cell in the bottom row should be filled
            boolean anyFilled = false;
            for (int col = 0; col < copy[bottomRow].length; col++) {
                if (copy[bottomRow][col] != '\0') {
                    anyFilled = true;
                    break;
                }
            }
            assertTrue(anyFilled);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void placeCurrentPolyomino_advancesQueue() {
        Platform.runLater(() -> {
            Polyomino before = board.peekNext();
            board.placeCurrentPolyomino();
            Polyomino after = board.getCurrent();
            // current should now be what was next in queue
            assertEquals(before, after);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // StepDown

    @Test
    void stepDown_returnsTrue_whenSpaceAvailable() {
        Platform.runLater(() -> {
            assertTrue(board.stepDown());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void stepDown_movesPieceDown() {
        Platform.runLater(() -> {
            double originalY = board.getCurrent().getShapes()[0].getY();
            board.stepDown();
            assertEquals(originalY + Board.BASE_GRID, board.getCurrent().getShapes()[0].getY());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void stepDown_returnsFalse_atBottom() {
        Platform.runLater(() -> {
            // Drop to bottom first
            board.placeCurrentPolyomino();
            // Get fresh piece and step until it can't go further
            boolean result = true;
            for (int i = 0; i < Board.Y_MAX / Board.BASE_GRID; i++) {
                result = board.stepDown();
            }
            assertFalse(result);
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Check if game over

    @Test
    void isGameOver_falseOnFreshBoard() {
        Platform.runLater(() -> {
            assertFalse(board.isGameOver());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void isGameOver_trueWhenTopRowOccupied() {
        Platform.runLater(() -> {
            // Fill the top row under where current piece spawns
            Polyomino current = board.getCurrent();
            for (Rectangle block : current.getShapes()) {
                int col = (int) block.getX() / Board.BASE_GRID;
                board.setLetter(0, col, 'x');
            }
            assertTrue(board.isGameOver());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Switch from current to reserved Polyomino

    @Test
    void switchReservedPolyomino_setsReserved_whenNullBefore() {
        Platform.runLater(() -> {
            assertNull(board.getReserved());
            Polyomino originalCurrent = board.getCurrent();
            board.switchReservedPolyomino();
            assertEquals(originalCurrent, board.getReserved());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Test
    void switchReservedPolyomino_swapsCorrectly_whenReservedExists() {
        Platform.runLater(() -> {
            board.switchReservedPolyomino(); // sets reserved
            Polyomino reservedPiece = board.getReserved();
            Polyomino currentPiece = board.getCurrent();

            board.switchReservedPolyomino(); // should swap

            assertEquals(reservedPiece, board.getCurrent());
            assertEquals(currentPiece, board.getReserved());
        });
        try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}