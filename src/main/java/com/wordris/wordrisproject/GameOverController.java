package com.wordris.wordrisproject;
 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
 
public class GameOverController {
 
    @FXML private Label finalScoreLabel;
    @FXML private Button restartButton;
    @FXML private Button menuButton;
 
    private int finalScore;
 
    public void initGameOver(int score) {
        this.finalScore = score;
        finalScoreLabel.setText(String.valueOf(score));
    }
 
    @FXML
    protected void onRestart() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/wordris/wordrisproject/game.fxml")
            );
            Scene gameScene = new Scene(loader.load());
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(gameScene);
            stage.setTitle("Wordris");
 
            GameController controller = loader.getController();
            controller.initGame(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @FXML
    protected void onMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/wordris/wordrisproject/menu.fxml")
            );
            Scene menuScene = new Scene(loader.load());
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(menuScene);
            stage.setTitle("Wordris");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 