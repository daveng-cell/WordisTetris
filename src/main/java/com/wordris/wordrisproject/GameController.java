package com.wordris.wordrisproject;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

        boardPane.getChildren().add(gameManager.getCurrentBoard().getVisualBoard());

        setupGameLoop();
        setupKeyHandlers();
        updateHUD();
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameManager.isRunning() && !gameManager.isPaused()) {
                    gameManager.updatePerFrame();
                    updateHUD();
                }
            }
        };
        gameLoop.start();
    }

    private void setupKeyHandlers() {
        boardPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (!gameManager.isRunning() || gameManager.isPaused()) return;
                    Board board = gameManager.getCurrentBoard();
                    switch (event.getCode()) {
                        case LEFT  -> board.moveCurrentPolyomino(-1);
                        case RIGHT -> board.moveCurrentPolyomino(1);
                        case DOWN  -> board.placeCurrentPolyomino();
                        case C     -> board.switchReservedPolyomino();
                        case P     -> onPause();
                        default    -> {}
                    }
                    updateHUD();
                });
            }
        });
    }

    private void updateHUD() {
        scoreLabel.setText(String.valueOf(gameManager.getCurrentRoundScore()));
        statusLabel.setText(gameManager.isPaused() ? "PAUSED" : "");
        // TODO: render nextPane1 with upcoming polyomino preview
        // TODO: render reservedPane with reserved polyomino
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
            updateHUD();
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