package com.wordris.wordrisproject;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameController {

    @FXML private Label scoreLabel;
    @FXML private Label statusLabel;
    @FXML private Pane  boardPane;
    @FXML private Pane  reservedPane;
    @FXML private Pane  nextPane1;
    @FXML private Button pauseButton;
    @FXML private Button exitButton;

    private GameManager gameManager;
    private AnimationTimer gameLoop;
    private Stage stage;

    public void initGame(Stage stage) {
        this.stage = stage;
        gameManager = new GameManager();
        gameManager.startGame();

        Pane visualBoard = gameManager.getCurrentBoard().getVisualBoard();
        visualBoard.setPrefSize(300, 600);
        boardPane.getChildren().add(visualBoard);

        pauseButton.setFocusTraversable(false);
        exitButton.setFocusTraversable(false);

        setupGameLoop();
        setupKeyHandlers();

        scoreLabel.setText("0");
        statusLabel.setText("");
        updateNextPreview();

        boardPane.setFocusTraversable(true);
        boardPane.requestFocus();
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameManager.isRunning() && !gameManager.isPaused()) {
                    gameManager.updatePerFrame();
                    scoreLabel.setText(String.valueOf(gameManager.getCurrentRoundScore()));
                    statusLabel.setText("");
                }
            }
        };
        gameLoop.start();
    }

    private void setupKeyHandlers() {
        boardPane.setFocusTraversable(true);
        boardPane.setOnKeyPressed(event -> {
            if (!gameManager.isRunning() || gameManager.isPaused()) return;
            Board board = gameManager.getCurrentBoard();
            System.out.println("Key pressed: " + event.getCode());
            switch (event.getCode()) {
                case LEFT -> board.moveCurrentPolyomino(-1);
                case RIGHT -> board.moveCurrentPolyomino(1);
                case DOWN -> {
                    board.placeCurrentPolyomino();
                    gameManager.onPiecePlaced();
                    scoreLabel.setText(String.valueOf(gameManager.getCurrentRoundScore()));
                    updateNextPreview();
                    updateReservePreview();
                }
                case C -> {
                    board.switchReservedPolyomino();
                    updateReservePreview();
                    updateNextPreview();
                }
                case P -> onPause();
                default -> {}
            }
            boardPane.requestFocus();
            event.consume();
        });
    }

    private void updateNextPreview() {
        nextPane1.getChildren().clear();
        Polyomino next = gameManager.getCurrentBoard().peekNext();
        if (next == null) return;

        char[] letters = next.getLetters();
        double paneWidth = nextPane1.getPrefWidth();
        double paneHeight = nextPane1.getPrefHeight();
        double blockSize = Math.min(paneWidth / letters.length, paneHeight);
        double startX = (paneWidth - blockSize * letters.length) / 2;
        double startY = (paneHeight - blockSize) / 2;

        for (int i = 0; i < letters.length; i++) {
            Rectangle rect = new Rectangle(
                startX + i * blockSize, startY,
                blockSize - 2, blockSize - 2
            );
            rect.setFill(javafx.scene.paint.Color.WHITE);
            rect.setStroke(javafx.scene.paint.Color.YELLOW);

            javafx.scene.text.Text text = new javafx.scene.text.Text(
                String.valueOf(letters[i]).toUpperCase()
            );
            text.setFill(javafx.scene.paint.Color.BLACK);
            text.setFont(Font.font("Courier New", FontWeight.BOLD, blockSize * 0.5));
            text.setX(startX + i * blockSize + blockSize * 0.25);
            text.setY(startY + blockSize * 0.65);

            nextPane1.getChildren().addAll(rect, text);
        }
    }

    private void updateReservePreview() {
        reservedPane.getChildren().clear();
        Polyomino reserved = gameManager.getCurrentBoard().getReserved();
        if (reserved == null) return;

        char[] letters = reserved.getLetters();
        double paneWidth = reservedPane.getPrefWidth();
        double paneHeight = reservedPane.getPrefHeight();
        double blockSize = Math.min(paneWidth / letters.length, paneHeight);
        double startX = (paneWidth - blockSize * letters.length) / 2;
        double startY = (paneHeight - blockSize) / 2;

        for (int i = 0; i < letters.length; i++) {
            Rectangle rect = new Rectangle(
                startX + i * blockSize, startY,
                blockSize - 2, blockSize - 2
            );
            rect.setFill(javafx.scene.paint.Color.WHITE);
            rect.setStroke(javafx.scene.paint.Color.YELLOW);

            javafx.scene.text.Text text = new javafx.scene.text.Text(
                String.valueOf(letters[i]).toUpperCase()
            );
            text.setFill(javafx.scene.paint.Color.BLACK);
            text.setFont(Font.font("Courier New", FontWeight.BOLD, blockSize * 0.5));
            text.setX(startX + i * blockSize + blockSize * 0.25);
            text.setY(startY + blockSize * 0.65);

            reservedPane.getChildren().addAll(rect, text);
        }
    }

    @FXML
    protected void onPause() {
        if (gameManager.isPaused()) {
            gameManager.resumeGame();
            pauseButton.setText("PAUSE");
            statusLabel.setText("");
        } else {
            gameManager.pauseGame();
            pauseButton.setText("RESUME");
            statusLabel.setText("PAUSED");
        }
    }

    @FXML
    protected void onSwapReserve() {
        if (gameManager.isRunning() && !gameManager.isPaused()) {
            gameManager.getCurrentBoard().switchReservedPolyomino();
            updateReservePreview();
            updateNextPreview();
            scoreLabel.setText(String.valueOf(gameManager.getCurrentRoundScore()));
        }
    }

    @FXML
    protected void onExit() {
        shutdown();
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/wordris/wordrisproject/menu.fxml")
            );
            Scene menuScene = new Scene(loader.load());
            stage.setScene(menuScene);
            stage.setTitle("Wordris");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (gameLoop != null) gameLoop.stop();
        gameManager.endGame();
    }
}